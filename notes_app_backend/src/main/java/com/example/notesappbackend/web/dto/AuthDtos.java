package com.example.notesappbackend.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * DTOs for authentication endpoints.
 */
public class AuthDtos {

    public static class RegisterRequest {
        @Schema(description = "Unique username", example = "ocean_user")
        @NotBlank
        public String username;

        @Schema(description = "Password", example = "StrongPass!123")
        @NotBlank
        public String password;
    }

    public static class LoginRequest {
        @Schema(description = "Username", example = "ocean_user")
        @NotBlank
        public String username;

        @Schema(description = "Password", example = "StrongPass!123")
        @NotBlank
        public String password;
    }

    public static class AuthResponse {
        @Schema(description = "JWT token to be used in Authorization header as Bearer <token>")
        public String token;

        public AuthResponse(String token) {
            this.token = token;
        }
    }
}
