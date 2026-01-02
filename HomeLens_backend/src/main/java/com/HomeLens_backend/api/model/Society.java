package com.HomeLens_backend.api.model;

import java.util.List;

public record Society(
    String id,
    String name,
    String address,
    String city,
    String area,
    String pincode,
    String imageUrl,
    int totalFlats,
    int reviewCount,
    double averageRating,
    List<String> amenities
) {}
