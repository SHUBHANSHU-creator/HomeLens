package com.HomeLens_backend.api.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class OtpService {

    private static final Duration OTP_TTL = Duration.ofMinutes(5);
    private static final Duration OTP_REQUEST_BLOCK_DURATION = Duration.ofMinutes(15);
    private static final int OTP_LENGTH = 6;
    private static final String OTP_KEY_PREFIX = "otp:";
    private static final String OTP_ATTEMPT_KEY_PREFIX = "otp:attempts:";
    private static final String OTP_BLOCK_KEY_PREFIX = "otp:block:";
    private static final int OTP_SEND_ATTEMPT_LIMIT = 3;

    private static final Logger log = LoggerFactory.getLogger(OtpService.class);
    private final Random random = new Random();
    private final StringRedisTemplate redisTemplate;



    public String sendOtp(String mobileNumber) {
        Duration blockedFor = getRemainingBlockDuration(mobileNumber);
        if (blockedFor != null) {
            throw new OtpRateLimitException(blockedFor);
        }

        Long attempts = redisTemplate.opsForValue().increment(buildAttemptsKey(mobileNumber));
        if (attempts != null && attempts == 1) {
            redisTemplate.expire(buildAttemptsKey(mobileNumber), OTP_REQUEST_BLOCK_DURATION);
        }

        if (attempts != null && attempts > OTP_SEND_ATTEMPT_LIMIT) {
            blockMobileNumber(mobileNumber);
            redisTemplate.delete(buildAttemptsKey(mobileNumber));
            throw new OtpRateLimitException(OTP_REQUEST_BLOCK_DURATION);
        }

        String otp = generateOtp();
        Instant expiresAt = Instant.now().plus(OTP_TTL);
        redisTemplate.opsForValue().set(buildKey(mobileNumber), otp, OTP_TTL);

        // In a production environment, this should integrate with an SMS gateway.
        log.info("Generated OTP {} for mobile {} (expires at {})", otp, mobileNumber, expiresAt);
        return otp;
    }

    public boolean verifyOtp(String mobileNumber, String otp) {
        String cachedOtp = redisTemplate.opsForValue().get(buildKey(mobileNumber));
        if (cachedOtp == null) {
            return false;
        }

        boolean matches = cachedOtp.equals(otp);
        if (matches) {
            redisTemplate.delete(buildKey(mobileNumber));
        }
        return matches;
    }

    private String generateOtp() {
        int bound = (int) Math.pow(10, OTP_LENGTH);
        int number = random.nextInt(bound);
        return String.format("%0" + OTP_LENGTH + "d", number);
    }

    private String buildKey(String mobileNumber) {
        return OTP_KEY_PREFIX + mobileNumber;
    }

    private String buildAttemptsKey(String mobileNumber) {
        return OTP_ATTEMPT_KEY_PREFIX + mobileNumber;
    }

    private String buildBlockKey(String mobileNumber) {
        return OTP_BLOCK_KEY_PREFIX + mobileNumber;
    }

    private Duration getRemainingBlockDuration(String mobileNumber) {
        Long seconds = redisTemplate.getExpire(buildBlockKey(mobileNumber), TimeUnit.SECONDS);
        if (seconds == null || seconds <= 0) {
            return null;
        }
        return Duration.ofSeconds(seconds);
    }

    private void blockMobileNumber(String mobileNumber) {
        redisTemplate.opsForValue().set(buildBlockKey(mobileNumber), "blocked", OTP_REQUEST_BLOCK_DURATION);
    }
}
