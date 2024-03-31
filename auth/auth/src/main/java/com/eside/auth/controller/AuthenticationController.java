package com.eside.auth.controller;

import com.eside.auth.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

@PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
){
    return ResponseEntity.ok(service.register(request));
}



@PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authentication(
            @RequestBody AuthenticationRequest request
){
    return ResponseEntity.ok(service.authenticate(request));
}



}
