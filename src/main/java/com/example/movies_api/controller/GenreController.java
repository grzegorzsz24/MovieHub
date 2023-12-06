package com.example.movies_api.controller;

import com.example.movies_api.dto.GenreDto;
import com.example.movies_api.dto.MovieDto;
import com.example.movies_api.service.GenreService;
import com.example.movies_api.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;
    private final MovieService movieService;

    @GetMapping("/{name}")
    public ResponseEntity<List<MovieDto>> getGenreMovies(@PathVariable String name) {
        GenreDto genre = genreService.findGenreByName(name);
        return ResponseEntity.ok(movieService.findMoviesByGenreName(name));
    }

    @GetMapping()
    public ResponseEntity<List<GenreDto>> getGenreList() {
        return ResponseEntity.ok(genreService.findAllGenres());
    }
}