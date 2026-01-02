package com.HomeLens_backend.api.model;

public record Flat(
    String id,
    String societyId,
    String flatNumber,
    int floor,
    int bedrooms,
    int bathrooms,
    int area,
    int reviewCount,
    double averageRating
) {}
