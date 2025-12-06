package com.HomeLens_backend.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "security.jwt")
@Data
public class JwtProperties {

    private String secretKey;
    private long expirationMinutes = 60;
}
