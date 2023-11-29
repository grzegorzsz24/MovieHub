package com.example.movies_api.repository;

import com.example.movies_api.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findAllByPromotedIsTrue();
    Optional<Movie> findByTitle(String title);
}
