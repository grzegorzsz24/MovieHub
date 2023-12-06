package com.example.movies_api.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class GenreDto {
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
}

