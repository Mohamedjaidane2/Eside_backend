package com.eside.auth.controller;

import com.eside.auth.dtos.AuthenticationRequest;
import com.eside.auth.dtos.AuthenticationResponse;
import com.eside.auth.dtos.RegisterRequest;
import com.eside.auth.dtos.UserDTO;
import com.eside.auth.model.User;
import com.eside.auth.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @GetMapping("/check-auth")
    public ResponseEntity<?> isExpired(@RequestHeader(name = "Authorization") String token) {
        if (token == null || token.isEmpty()) {
            return ResponseEntity.badRequest().body(false);
        }

        return ResponseEntity.ok(service.isExpired(token));
    }

    @GetMapping("/get/info")
    public ResponseEntity<UserDTO> getCurrentUserInfo(@RequestHeader(name = "Authorization") String token) {
        return ResponseEntity.ok(service.getCurrentUserInfo(token));
    }


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authentication(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }


}
