package com.eside.auth.service;

import com.eside.auth.client.AccountClient;
import com.eside.auth.config.JwtService;
import com.eside.auth.controller.AuthenticationRequest;
import com.eside.auth.controller.AuthenticationResponse;
import com.eside.auth.controller.RegisterRequest;
import com.eside.auth.externalData.Account;
import com.eside.auth.externalData.NewAccountDto;
import com.eside.auth.model.Role;
import com.eside.auth.model.User;
import com.eside.auth.repository.RoleRepository;
import com.eside.auth.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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


    public AuthenticationResponse register(RegisterRequest request) {
        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new NotFoundException("Role not found!"));

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
