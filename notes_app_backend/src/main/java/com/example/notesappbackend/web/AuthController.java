package com.example.notesappbackend.web;

import com.example.notesappbackend.security.JwtTokenService;
import com.example.notesappbackend.service.UserService;
import com.example.notesappbackend.web.dto.AuthDtos;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

/**
 * Authentication endpoints: register and login to receive JWT tokens.
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "User authentication endpoints (Ocean Professional)")
public class AuthController {

    private final UserService userService;
    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserService userService, JwtTokenService jwtTokenService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtTokenService = jwtTokenService;
        this.authenticationManager = authenticationManager;
    }

    // PUBLIC_INTERFACE
    @PostMapping("/register")
    @Operation(summary = "Register", description = "Create a new user account.")
    public ResponseEntity<AuthDtos.AuthResponse> register(@Valid @RequestBody AuthDtos.RegisterRequest req) {
        userService.register(req.username, req.password);
        String token = jwtTokenService.generateToken(req.username);
        return ResponseEntity.ok(new AuthDtos.AuthResponse(token));
    }

    // PUBLIC_INTERFACE
    @PostMapping("/login")
    @Operation(summary = "Login", description = "Authenticate and receive JWT token.")
    public ResponseEntity<?> login(@Valid @RequestBody AuthDtos.LoginRequest req) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.username, req.password));
            String token = jwtTokenService.generateToken(req.username);
            return ResponseEntity.ok(new AuthDtos.AuthResponse(token));
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}
