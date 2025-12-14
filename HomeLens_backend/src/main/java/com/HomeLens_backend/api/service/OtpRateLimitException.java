package com.HomeLens_backend.api.service;

import java.time.Duration;

/**
 * Thrown when a mobile number exceeds the allowed OTP send attempts.
 */
public class OtpRateLimitException extends RuntimeException {

    private final Duration retryAfter;

    public OtpRateLimitException(Duration retryAfter) {
        super("Too many OTP requests. Try again later.");
        this.retryAfter = retryAfter;
    }

    public Duration getRetryAfter() {
        return retryAfter;
    }
}
