package com.example.movies_api.controller;

import com.example.movies_api.dto.GenreDto;
import com.example.movies_api.model.Genre;
import com.example.movies_api.service.GenreService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class GenreManagementController {
    private final GenreService genreService;
    private final ObjectMapper objectMapper;

    @PostMapping("/add-genre")
    public ResponseEntity<Genre> addGenre(@RequestBody GenreDto genreDto) {
        Genre savedGenre = genreService.addGenre(genreDto);
        URI savedGenreUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedGenre.getId())
                .toUri();
        return ResponseEntity.created(savedGenreUri).body(savedGenre);
    }

    @PatchMapping("/update_genre/{id}")
    ResponseEntity<?> updateGenre(@PathVariable Long id, @RequestBody JsonMergePatch patch) {
        try {
            GenreDto genreDto = genreService.findGenreById(id);
            GenreDto genrePatched = applyPatch(genreDto, patch);
            genreService.updateGenre(genrePatched);

        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.internalServerError().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    private GenreDto applyPatch(GenreDto genreDto, JsonMergePatch patch) throws JsonPatchException, JsonProcessingException {
        JsonNode genreNode = objectMapper.valueToTree(genreDto);
        JsonNode genrePatchedNode = patch.apply(genreNode);
        return objectMapper.treeToValue(genrePatchedNode, GenreDto.class);
    }

    @DeleteMapping("/delete-genre/{id}")
    public ResponseEntity<?> deleteGenre(@PathVariable long id) {
        genreService.deleteGenre(id);
        return ResponseEntity.noContent().build();
    }
}

