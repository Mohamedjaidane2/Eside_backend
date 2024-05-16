package com.eside.auth.service;

import com.eside.EmailSender.model.EmailTemplateName;
import com.eside.auth.client.AccountClient;
import com.eside.auth.client.EmailClient;
import com.eside.auth.config.JwtService;
import com.eside.auth.dtos.AuthenticationRequest;
import com.eside.auth.dtos.AuthenticationResponse;
import com.eside.auth.dtos.RegisterRequest;
import com.eside.auth.dtos.UserDTO;
import com.eside.auth.exception.EntityNotFoundException;
import com.eside.auth.exception.InvalidOperationException;
import com.eside.auth.externalData.Account;
import com.eside.auth.externalData.EmailStructure;
import com.eside.auth.externalData.NewAccountDto;
import com.eside.auth.model.Role;
import com.eside.auth.model.Token;
import com.eside.auth.model.User;
import com.eside.auth.repository.RoleRepository;
import com.eside.auth.repository.TokenRepository;
import com.eside.auth.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
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

import java.security.SecureRandom;
import java.time.LocalDateTime;
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
    private final TokenRepository tokenRepository;
    private final EmailClient emailClient;
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
        //System.out.println("accounttttt======== "+account);
        newUser.setAccountId(account.getId());

        repository.save(newUser);

        String jwtToken = jwtService.generateToken(newUser);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }



    public void registerWithEmailValidation(RegisterRequest request) throws EntityNotFoundException, MessagingException {
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
        sendValidationEmail(newUser);

    }

    private void sendValidationEmail(User newUser) throws MessagingException {
        var newToken = generateAndSaveActivationToken(newUser);
        String activationUrl = "http://localhost:4200/activate-account";
        EmailStructure emailStructure = EmailStructure.builder()
                .to(newUser.getEmail())
                .userName(newUser.getFirstName()+" "+newUser.getLastName())
                .emailTemplate(EmailTemplateName.ACTIVATE_ACCOUNT)
                .confirmationUrl(activationUrl)
                .activationCode(newToken)
                .subject("Account activation")
                        .build();
        emailClient.sendMail(emailStructure);
        //Send email
    }

    private String generateAndSaveActivationToken(User newUser) {
        String generatedToken = generateActivationCode(6);
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(newUser)
                .build();
        tokenRepository.save(token);
        return generatedToken;
    }

    private String generateActivationCode(int length) {
        String caracters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i =0 ; i< length;i++){
           int randomIndex = secureRandom.nextInt(caracters.length());
           codeBuilder.append(caracters.charAt(randomIndex));
        }
        return codeBuilder.toString();
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
    public UserDTO getCurrentUserInfo(String token) {
        String jwt = token.substring(7, token.length());
        logger.info("User's token: {}", jwt);
        String email = jwtService.extractUsername(jwt);
        logger.info("Email's user: {}", email);
        Optional<User> user =  repository.findByEmail(email);
        if(user.isEmpty()){
            throw new EntityNotFoundException("User not found!");

        }
        return UserDTO.builder()
                .id(user.get().getId())
                .firstName(user.get().getFirstName())
                .lastName(user.get().getLastName())
                .email(user.get().getUsername())
                .accountId(user.get().getAccountId())
                .role(user.get().getRole().getName()).build();

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

    //@Transactional
    public void activateAccount(String token) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token)
                // todo exception has to be defined
                .orElseThrow(() -> new RuntimeException("Invalid token"));
        if (LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
            sendValidationEmail(savedToken.getUser());
            throw new RuntimeException("Activation token has expired. A new token has been send to the same email address");
        }

        var user = repository.findById(savedToken.getUser().getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setEnabled(true);
        repository.save(user);

        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }

}
