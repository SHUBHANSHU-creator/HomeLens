package com.HomeLens_backend.api.controller;

import com.HomeLens_backend.api.data.MockData;
import com.HomeLens_backend.api.model.Flat;
import com.HomeLens_backend.api.model.Society;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/societies")
public class SocietyController {

    @GetMapping
    public List<Society> listSocieties() {
        return MockData.SOCIETIES;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Society> getSociety(@PathVariable String id) {
        return MockData.findSociety(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/flats")
    public List<Flat> listFlats(@PathVariable String id) {
        return MockData.flatsBySociety(id);
    }
}
