package com.example.movies_api.mapper;

import com.example.movies_api.dto.MovieDto;
import com.example.movies_api.model.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MovieDtoMapper {

    public static MovieDto map(Movie movie) {
        MovieDto movieDto = new MovieDto();
        BeanUtils.copyProperties(movie, movieDto);

        return movieDto;
    }

}