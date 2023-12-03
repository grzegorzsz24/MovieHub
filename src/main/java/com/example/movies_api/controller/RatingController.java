package com.example.movies_api.controller;

import com.example.movies_api.dto.RatingDto;
import com.example.movies_api.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ratings")
public class RatingController {
    private final RatingService ratingService;

    @PostMapping("/rate-movie")
    public ResponseEntity<RatingDto> addRating(@RequestParam long movieId, @RequestParam int rating) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        RatingDto savedRating = ratingService.addOrUpdateRating(currentUserEmail, movieId, rating);
        URI savedRatingUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedRating.getId())
                .toUri();
        return ResponseEntity.created(savedRatingUri).body(savedRating);
    }
}