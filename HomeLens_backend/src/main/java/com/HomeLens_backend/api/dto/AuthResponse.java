package com.HomeLens_backend.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class AuthResponse {

    private String token;
    private Instant expiresAt;
//    private String tokenType = "Bearer";
}
