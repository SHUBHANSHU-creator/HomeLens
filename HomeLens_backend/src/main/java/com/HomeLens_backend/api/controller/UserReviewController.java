package com.HomeLens_backend.api.controller;

import com.HomeLens_backend.api.data.MockData;
import com.HomeLens_backend.api.model.Review;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserReviewController {

    @GetMapping("/{userId}/reviews")
    public List<Review> listUserReviews(@PathVariable String userId) {
        return MockData.reviewsByUser(userId);
    }
}
