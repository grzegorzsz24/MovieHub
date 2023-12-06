package com.example.movies_api.service;

import com.example.movies_api.dto.MovieDto;
import com.example.movies_api.dto.MovieGenresDto;
import com.example.movies_api.dto.MovieSaveDto;
import com.example.movies_api.exception.BadRequestException;
import com.example.movies_api.exception.ResourceNotFoundException;
import com.example.movies_api.mapper.MovieDtoMapper;
import com.example.movies_api.model.Genre;
import com.example.movies_api.model.Movie;
import com.example.movies_api.repository.GenreRepository;
import com.example.movies_api.repository.MovieRepository;
import com.example.movies_api.storage.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final FileStorageService fileStorageService;
    private final MovieDtoMapper mapper;

    public List<MovieDto> findAllPromotedMovies() {
        return movieRepository.findAllByPromotedIsTrue().stream()
                .map(MovieDtoMapper::map)
                .toList();
    }

    public MovieDto findMovieById(long id) {
        return movieRepository.findById(id).map(MovieDtoMapper::map)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
    }

    public MovieGenresDto findMovieDtoById(long id) {
        return movieRepository.findById(id).map(MovieDtoMapper::mapToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
    }

    public List<MovieDto> findMoviesByGenreName(String genre) {
        return movieRepository.findAllByGenre_NameIgnoreCase(genre).stream()
                .map(MovieDtoMapper::map)
                .toList();
    }

    public Movie addMovie(MovieSaveDto movieToSave) {
        if (movieRepository.findByTitle(movieToSave.getTitle()).isPresent()) {
            throw new BadRequestException("Movie with this title already exists");
        }
        Movie movie = new Movie();
        movie.setTitle(movieToSave.getTitle());
        movie.setOriginalTitle(movieToSave.getOriginalTitle());
        movie.setPromoted(movieToSave.isPromoted());
        movie.setReleaseYear(movieToSave.getReleaseYear());
        movie.setShortDescription(movieToSave.getShortDescription());
        movie.setDescription(movieToSave.getDescription());
        movie.setYoutubeTrailerId(movieToSave.getYoutubeTrailerId());
        Genre genre = genreRepository.findByNameIgnoreCase(movieToSave.getGenre())
                .orElseThrow(() -> new ResourceNotFoundException("Gennre not found"));
        movie.setGenre(genre);
        if (movieToSave.getPoster() != null) {
            String savedFileName = fileStorageService.saveImage(movieToSave.getPoster());
            movie.setPoster(savedFileName);
        }
        return movieRepository.save(movie);
    }

    public List<MovieDto> findTopMovies(int size) {
        Pageable page = Pageable.ofSize(size);
        return movieRepository.findTopByRating(page).stream()
                .map(MovieDtoMapper::map)
                .toList();
    }

    public void updateMovie(MovieGenresDto movieToUpdate) {
        Movie movie = mapper.map(movieToUpdate);
        movieRepository.save(movie);
    }

    public void deleteMovie(long id) {
        movieRepository.deleteById(id);
    }

    public List<MovieDto> findAllWithFilters(String genre, Integer releaseYear, int page) {
        Pageable size = PageRequest.of(page, 10);
        return movieRepository.findAllByGenre_NameAndReleaseYear(genre, releaseYear, size).stream()
                .map(MovieDtoMapper::map)
                .toList();
    }
}
