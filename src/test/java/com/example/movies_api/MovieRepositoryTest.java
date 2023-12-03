package com.example.movies_api;

import com.example.movies_api.model.Movie;
import com.example.movies_api.model.Rating;
import com.example.movies_api.repository.MovieRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@DataJpaTest
public class MovieRepositoryTest {
    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void
    givenPageable_whenFindTopByRating_thenReturnNonEmptySortedMoviesList() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Movie> topRatedMovies = movieRepository.findTopByRating(pageable);
        Assertions.assertFalse(topRatedMovies.isEmpty());

        boolean areAveragesSorted = true;
        for (int i = 0; i < topRatedMovies.size() - 1; i++) {
            double avgRatingCurrent = topRatedMovies.get(i).getRatings().stream()
                    .mapToDouble(Rating::getRating)
                    .average()
                    .getAsDouble();
            double avgRatingNext = topRatedMovies.get(i + 1).getRatings().stream()
                    .mapToDouble(Rating::getRating)
                    .average()
                    .getAsDouble();
            if (avgRatingCurrent < avgRatingNext) {
                areAveragesSorted = false;
                break;
            }
        }
        Assertions.assertTrue(areAveragesSorted);
    }

    @Test
    public void givenGenreAndReleaseYear_whenFindAllByGenreAndYear_thenReturnMatchingMoviesList() {
        String genre = "Action";
        Integer releaseYear = 2020;
        Pageable pageable = PageRequest.of(0, 10);
        List<Movie> moviesByGenreAndYear = movieRepository.findAllByGenre_NameAndReleaseYear(genre, releaseYear, pageable);
        Assertions.assertFalse(moviesByGenreAndYear.isEmpty());

        boolean allMoviesMatchCriteria = moviesByGenreAndYear.stream()
                .allMatch(movie -> movie.getGenre().getName().equalsIgnoreCase(genre) && movie.getReleaseYear().equals(releaseYear));

        Assertions.assertTrue(allMoviesMatchCriteria);
    }

}
