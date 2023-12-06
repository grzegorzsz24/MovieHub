package com.example.movies_api.dto;

import com.example.movies_api.model.Rating;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
public class MovieGenresDto {
    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String originalTitle;
    @NotBlank
    private String shortDescription;
    @NotBlank
    private String description;
    @NotBlank
    private String youtubeTrailerId;
    @NotNull
    private Integer releaseYear;
    @NotBlank
    private String genre;
    @NotNull
    private boolean promoted;
    @NotBlank
    private String poster;
    private Set<Rating> ratings;
}
