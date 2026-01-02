package com.HomeLens_backend.api.data;

import com.HomeLens_backend.api.model.Flat;
import com.HomeLens_backend.api.model.Review;
import com.HomeLens_backend.api.model.Society;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

public final class MockData {
    public static final List<Society> SOCIETIES = List.of(
        new Society(
            "1",
            "Green Valley Heights",
            "123 Main Road, Sector 15",
            "Gurugram",
            "Sector 15",
            "122001",
            "https://images.unsplash.com/photo-1545324418-cc1a3fa10c00?w=800&auto=format&fit=crop",
            250,
            45,
            4.2,
            List.of("Swimming Pool", "Gym", "Club House", "Garden", "Parking")
        ),
        new Society(
            "2",
            "Sunrise Apartments",
            "456 Park Avenue, DLF Phase 3",
            "Gurugram",
            "DLF Phase 3",
            "122002",
            "https://images.unsplash.com/photo-1486406146926-c627a92ad1ab?w=800&auto=format&fit=crop",
            180,
            32,
            4.5,
            List.of("Gym", "Garden", "Parking", "24x7 Security")
        ),
        new Society(
            "3",
            "Royal Residency",
            "789 Golf Course Road",
            "Gurugram",
            "Golf Course Road",
            "122003",
            "https://images.unsplash.com/photo-1574362848149-11496d93a7c7?w=800&auto=format&fit=crop",
            320,
            78,
            4.7,
            List.of("Swimming Pool", "Gym", "Club House", "Tennis Court", "Garden", "Parking", "Spa")
        ),
        new Society(
            "4",
            "Palm Gardens",
            "321 Sohna Road",
            "Gurugram",
            "Sohna Road",
            "122004",
            "https://images.unsplash.com/photo-1512917774080-9991f1c4c750?w=800&auto=format&fit=crop",
            200,
            28,
            3.9,
            List.of("Garden", "Parking", "Playground")
        ),
        new Society(
            "5",
            "The Crest",
            "555 MG Road, Sector 56",
            "Gurugram",
            "Sector 56",
            "122005",
            "https://images.unsplash.com/photo-1460317442991-0ec209397118?w=800&auto=format&fit=crop",
            450,
            112,
            4.4,
            List.of("Swimming Pool", "Gym", "Club House", "Garden", "Parking", "Indoor Games")
        )
    );

    public static final List<Flat> FLATS = List.of(
        new Flat("1", "1", "A-101", 1, 2, 2, 1200, 5, 4.3),
        new Flat("2", "1", "B-502", 5, 3, 3, 1800, 8, 4.1),
        new Flat("3", "1", "C-303", 3, 2, 2, 1100, 3, 4.5),
        new Flat("4", "2", "A-201", 2, 3, 2, 1600, 6, 4.6),
        new Flat("5", "2", "B-401", 4, 2, 2, 1250, 4, 4.2),
        new Flat("6", "3", "T1-1501", 15, 4, 4, 2500, 12, 4.8),
        new Flat("7", "3", "T2-801", 8, 3, 3, 2000, 9, 4.6)
    );

    public static final List<Review> REVIEWS = List.of(
        new Review(
            "1",
            "1",
            "u1",
            "Rahul S.",
            4,
            "Great location but noisy neighbors",
            "Lived here for 2 years. The location is excellent with easy access to metro and markets. However, the neighbors on the upper floor can be quite noisy during evenings.",
            List.of("Good location", "Spacious rooms", "Well-maintained"),
            List.of("Noisy neighbors", "Slow elevator"),
            null,
            null,
            true,
            Instant.parse("2024-01-15T00:00:00Z"),
            Instant.parse("2024-01-15T00:00:00Z")
        ),
        new Review(
            "2",
            "1",
            "u2",
            "Priya M.",
            5,
            "Perfect for families",
            "This is an ideal flat for families. Good ventilation, natural light, and the society has excellent amenities for children.",
            List.of("Family-friendly", "Good ventilation", "Nice amenities"),
            List.of("Parking can be tight on weekends"),
            null,
            null,
            true,
            Instant.parse("2024-02-20T00:00:00Z"),
            Instant.parse("2024-02-20T00:00:00Z")
        ),
        new Review(
            "3",
            "6",
            "u3",
            "Amit K.",
            5,
            "Luxury living at its best",
            "Absolutely stunning flat with breathtaking views. The build quality is excellent and the society maintenance is top-notch.",
            List.of("Amazing views", "Premium finish", "Great maintenance", "Security"),
            List.of("Expensive maintenance charges"),
            null,
            null,
            true,
            Instant.parse("2024-03-10T00:00:00Z"),
            Instant.parse("2024-03-10T00:00:00Z")
        )
    );

    private MockData() {}

    public static Optional<Society> findSociety(String id) {
        return SOCIETIES.stream().filter(society -> society.id().equals(id)).findFirst();
    }

    public static Optional<Flat> findFlat(String id) {
        return FLATS.stream().filter(flat -> flat.id().equals(id)).findFirst();
    }

    public static List<Flat> flatsBySociety(String societyId) {
        return FLATS.stream().filter(flat -> flat.societyId().equals(societyId)).toList();
    }

    public static List<Review> reviewsByFlat(String flatId) {
        return REVIEWS.stream().filter(review -> review.flatId().equals(flatId)).toList();
    }

    public static List<Review> reviewsByUser(String userId) {
        return REVIEWS.stream().filter(review -> review.userId().equals(userId)).toList();
    }
}
