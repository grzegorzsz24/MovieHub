package com.example.movies_api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieGenresDto {
    private Long id;
    private String title;
    private String originalTitle;
    private String shortDescription;
    private String description;
    private String youtubeTrailerId;
    private Integer releaseYear;
    private String genre;
    private boolean promoted;
    private String poster;
}
