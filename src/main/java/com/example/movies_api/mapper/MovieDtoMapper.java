package com.example.movies_api.mapper;

import com.example.movies_api.dto.MovieDto;
import com.example.movies_api.dto.MovieGenresDto;
import com.example.movies_api.model.Genre;
import com.example.movies_api.model.Movie;
import com.example.movies_api.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class MovieDtoMapper {
    private final GenreRepository genreRepository;

    public static MovieDto map(Movie movie) {
        MovieDto movieDto = new MovieDto();
        BeanUtils.copyProperties(movie, movieDto);

        if (movie.getGenre() != null) {
            movieDto.setGenre(movie.getGenre().getName());
        } else {
            movieDto.setGenre("Brak informacji");
        }

        return movieDto;
    }

    public Movie map(MovieGenresDto movieDto) {
        Movie movie = new Movie();
        BeanUtils.copyProperties(movieDto, movie);
        Genre genre = genreRepository.findByNameIgnoreCase(movieDto.getGenre()).get();
        movie.setGenre(genre);
        return movie;
    }

    public static MovieGenresDto mapToDto(Movie movie) {
        MovieGenresDto movieGenresDto = new MovieGenresDto();
        BeanUtils.copyProperties(movie, movieGenresDto);
        movieGenresDto.setGenre(movie.getGenre().getName());
        return movieGenresDto;
    }
}