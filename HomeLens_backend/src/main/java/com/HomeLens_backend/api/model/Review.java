package com.HomeLens_backend.api.model;

import java.time.Instant;
import java.util.List;

public record Review(
    String id,
    String flatId,
    String userId,
    String userName,
    int rating,
    String title,
    String content,
    List<String> pros,
    List<String> cons,
    String agreementUrl,
    String lightBillUrl,
    boolean isVerified,
    Instant createdAt,
    Instant updatedAt
) {}
