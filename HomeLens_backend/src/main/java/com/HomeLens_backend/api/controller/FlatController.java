package com.HomeLens_backend.api.controller;

import com.HomeLens_backend.api.data.MockData;
import com.HomeLens_backend.api.model.Flat;
import com.HomeLens_backend.api.model.Review;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/flats")
public class FlatController {

    @GetMapping("/{id}")
    public ResponseEntity<Flat> getFlat(@PathVariable String id) {
        return MockData.findFlat(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/reviews")
    public List<Review> listReviews(@PathVariable String id) {
        return MockData.reviewsByFlat(id);
    }
}
