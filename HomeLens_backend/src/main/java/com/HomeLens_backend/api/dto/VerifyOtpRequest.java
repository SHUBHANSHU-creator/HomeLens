package com.HomeLens_backend.api.dto;


import lombok.Data;

@Data
public class VerifyOtpRequest {
    private String mobileNumber;
    private String otp;
    private String deviceId;
}
