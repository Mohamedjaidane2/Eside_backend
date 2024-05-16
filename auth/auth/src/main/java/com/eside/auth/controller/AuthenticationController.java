package com.eside.auth.controller;

import com.eside.auth.dtos.AuthenticationRequest;
import com.eside.auth.dtos.AuthenticationResponse;
import com.eside.auth.dtos.RegisterRequest;
import com.eside.auth.dtos.UserDTO;
import com.eside.auth.model.User;
import com.eside.auth.service.AuthenticationService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> register(
            @RequestBody @Valid RegisterRequest request
    ) throws MessagingException {
        service.registerWithEmailValidation(request);
        return ResponseEntity.accepted().build();
    }


    @GetMapping("/check-auth/{token}")
    public ResponseEntity<?> isExpired(@PathVariable String token) {
        if (token == null || token.isEmpty()) {
            return ResponseEntity.badRequest().body(false);
        }

        return ResponseEntity.ok(service.isExpired(token));
    }

    @GetMapping("/get/info")
    public ResponseEntity<UserDTO> getCurrentUserInfo(@RequestHeader(name = "Authorization") String token) {
        return ResponseEntity.ok(service.getCurrentUserInfo(token));
    }

    @GetMapping("/activate-account")
    public void confirm(@RequestParam String token) throws MessagingException {
        service.activateAccount(token);
    }


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authentication(
            @RequestBody @Valid AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }


}
