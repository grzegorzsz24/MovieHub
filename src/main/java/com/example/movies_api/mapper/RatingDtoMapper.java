package com.example.movies_api.mapper;

import com.example.movies_api.dto.RatingDto;
import com.example.movies_api.model.Rating;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RatingDtoMapper {
    public static RatingDto map(Rating rating) {
        RatingDto ratingDto = new RatingDto();
        BeanUtils.copyProperties(rating, ratingDto);
        return ratingDto;
    }
}
