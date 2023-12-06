package com.example.movies_api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class MovieSaveDto {
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
    @NotNull
    private MultipartFile poster;
}
