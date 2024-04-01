package com.eside.auth.service;

import com.eside.auth.client.AccountClient;
import com.eside.auth.config.JwtService;
import com.eside.auth.dtos.AuthenticationRequest;
import com.eside.auth.dtos.AuthenticationResponse;
import com.eside.auth.dtos.RegisterRequest;
import com.eside.auth.exception.EntityNotFoundException;
import com.eside.auth.exception.InvalidOperationException;
import com.eside.auth.externalData.Account;
import com.eside.auth.externalData.NewAccountDto;
import com.eside.auth.model.Role;
import com.eside.auth.model.User;
import com.eside.auth.repository.RoleRepository;
import com.eside.auth.repository.UserRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    @Autowired
    private  AccountClient accountClient;
    Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    public AuthenticationResponse register(RegisterRequest request) throws EntityNotFoundException {
        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new EntityNotFoundException("Role not found!"));

        repository.findByEmail(request.getEmail())
                .ifPresent(user -> {
                    throw new EntityNotFoundException("User with this email already exists!");
                });

        User newUser = buildUserFromRequest(request, userRole);
        Account account = createAccountFromRequest(request);
        newUser.setAccountId(account.getId());

        repository.save(newUser);

        String jwtToken = jwtService.generateToken(newUser);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private User buildUserFromRequest(RegisterRequest request, Role userRole) {
        return User.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .loginStatus(false)
                .password(passwordEncoder.encode(request.getPassword()))
                .role(userRole)
                .build();
    }

    private Account createAccountFromRequest(RegisterRequest request) {
        return accountClient.createAccountFromAuth(NewAccountDto.builder()
                .firstName(request.getFirstname())
                .LastName(request.getLastname())
                .build());
    }
    public User getCurrentUserInfo(String token) {
        String jwt = token.substring(7, token.length());
        logger.info("User's token: {}", jwt);
        String email = jwtService.extractUsername(jwt);
        logger.info("Email's user: {}", email);
        Optional<User> user =  repository.findByEmail(email);
        if(user.isEmpty()){
            throw new EntityNotFoundException("User not found!");

        }
        return user.get();
    }

    public Boolean isExpired(String token) {
        String jwt = token.substring(7, token.length());
        if(jwt.equals("null")) return false;
        boolean validate = jwtService.validateAccessToken(jwt);
        if (!validate) return validate;
        boolean isExpired = jwtService.isTokenExpired(jwt);

        return !isExpired && validate;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword())

        );

        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
