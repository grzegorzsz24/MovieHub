package com.example.movies_api.mapper;

import com.example.movies_api.dto.GenreDto;
import com.example.movies_api.model.Genre;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class GenreDtoMapper {
    public static GenreDto map(Genre genre) {
        GenreDto genreDto = new GenreDto();
        BeanUtils.copyProperties(genre, genreDto);
        return genreDto;
    }

    public Genre mapToGenre(GenreDto genreDto) {
        Genre genre = new Genre();
        BeanUtils.copyProperties(genreDto, genre);
        return genre;
    }
}
