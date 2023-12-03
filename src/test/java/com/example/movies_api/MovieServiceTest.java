package com.example.movies_api;

import com.example.movies_api.dto.MovieDto;
import com.example.movies_api.mapper.MovieDtoMapper;
import com.example.movies_api.model.Movie;
import com.example.movies_api.model.Rating;
import com.example.movies_api.repository.MovieRepository;
import com.example.movies_api.service.MovieService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MovieServiceTest {
    @Mock
    private MovieRepository movieRepository;
    private AutoCloseable autoCloseable;
    @InjectMocks
    private MovieService movieService;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void givenPromotedMovies_whenFindAllPromoted_thenReturnMovieDtoListWithCorrectSize() {
        List<Movie> promotedMovies = createMovies();
        when(movieRepository.findAllByPromotedIsTrue()).thenReturn(promotedMovies);

        List<MovieDto> expectedMovieDtos = promotedMovies.stream()
                .map(MovieDtoMapper::map)
                .toList();
        List<MovieDto> actualMovieDtos = movieService.findAllPromotedMovies();
        assertEquals(expectedMovieDtos.size(), actualMovieDtos.size());
    }

    private static List<Movie> createMovies() {
        List<Movie> promotedMovies = new ArrayList<>();
        promotedMovies.add(Movie.builder()
                .id(1L)
                .title("Inception")
                .originalTitle("Inception")
                .shortDescription("A thief who enters the dreams of others")
                .description("Inception is a science fiction action film...")
                .youtubeTrailerId("1234")
                .releaseYear(2010)
                .promoted(true)
                .ratings(createRatings())
                .build());
        promotedMovies.add(Movie.builder()
                .id(2L)
                .title("Film2")
                .originalTitle("Film2")
                .shortDescription("Short description")
                .description("Description")
                .youtubeTrailerId("1234")
                .releaseYear(2010)
                .promoted(false)
                .ratings(createRatings())
                .build());
        return promotedMovies;
    }

    private static Set<Rating> createRatings() {
        Set<Rating> ratings = new HashSet<>();
        ratings.add(Rating.builder()
                .rating(4)
                .id(1L)
                .build());
        return ratings;
    }
}