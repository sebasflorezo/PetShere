package com.PetShere.presentation.controllers.auth;

import com.PetShere.presentation.dto.auth.LoginRequest;
import com.PetShere.presentation.dto.auth.RegisterRequest;
import com.PetShere.service.implementation.auth.AuthService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) throws MessagingException {
        return ResponseEntity.ok(authService.register(registerRequest));
    }

    // TODO: Hacer método de refresco de token
}
