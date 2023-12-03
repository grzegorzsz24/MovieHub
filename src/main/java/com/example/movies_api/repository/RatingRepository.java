package com.example.movies_api.repository;

import com.example.movies_api.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    Optional<Rating> findByUser_EmailAndMovie_Id(String userEmail, Long movieId);
}
