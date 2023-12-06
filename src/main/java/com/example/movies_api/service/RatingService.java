package com.example.movies_api.service;

import com.example.movies_api.dto.RatingDto;
import com.example.movies_api.exception.ResourceNotFoundException;
import com.example.movies_api.mapper.RatingDtoMapper;
import com.example.movies_api.model.Movie;
import com.example.movies_api.model.Rating;
import com.example.movies_api.model.User;
import com.example.movies_api.repository.MovieRepository;
import com.example.movies_api.repository.RatingRepository;
import com.example.movies_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    public RatingDto addOrUpdateRating(String userEmail, long movieId, int rating) {
        Rating ratingToSaveOrUpdate = ratingRepository.findByUser_EmailAndMovie_Id(userEmail, movieId)
                .orElseGet(Rating::new);
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
        ratingToSaveOrUpdate.setUser(user);
        ratingToSaveOrUpdate.setMovie(movie);
        ratingToSaveOrUpdate.setRating(rating);
        ratingRepository.save(ratingToSaveOrUpdate);
        return RatingDtoMapper.map(ratingToSaveOrUpdate);
    }
}