package com.example.movies_api.service;


import com.example.movies_api.dto.GenreDto;
import com.example.movies_api.exception.BadRequestException;
import com.example.movies_api.exception.ResourceNotFoundException;
import com.example.movies_api.mapper.GenreDtoMapper;
import com.example.movies_api.model.Genre;
import com.example.movies_api.repository.GenreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;
    private final GenreDtoMapper genreDtoMapper;

    public GenreDto findGenreByName(String name) {
        return genreRepository.findByNameIgnoreCase(name)
                .map(GenreDtoMapper::map).orElseThrow(() -> new ResourceNotFoundException("Genre not found"));
    }

    public List<GenreDto> findAllGenres() {
        return StreamSupport.stream(genreRepository.findAll().spliterator(), false)
                .map(GenreDtoMapper::map)
                .toList();
    }

    public Genre addGenre(GenreDto genreDto) {
        if (genreRepository.findByNameIgnoreCase(genreDto.getName()).isPresent()) {
            throw new BadRequestException("Genre already exists");
        }
        Genre genreToSave = new Genre();
        genreToSave.setName(genreDto.getName());
        genreToSave.setDescription(genreDto.getDescription());
        genreRepository.save(genreToSave);
        return genreToSave;
    }

    public GenreDto findGenreById(long id) {
        return genreRepository.findById(id).map(GenreDtoMapper::map)
                .orElseThrow(() -> new ResourceNotFoundException("Genre not found"));
    }

    public void updateGenre(GenreDto genreDto) {
        Genre genre = genreDtoMapper.mapToGenre(genreDto);
        genreRepository.save(genre);
    }

    public void deleteGenre(long id) {
        genreRepository.deleteById(id);
    }
}