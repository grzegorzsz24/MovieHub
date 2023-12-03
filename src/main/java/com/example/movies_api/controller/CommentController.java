package com.example.movies_api.controller;

import com.example.movies_api.model.Comment;
import com.example.movies_api.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/add-comment")
    public ResponseEntity<Comment> addComment(@RequestParam long movieId, @RequestParam String commentContent) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Comment savedComment = commentService.addOrUpdateComment(currentUserEmail, movieId, commentContent);
        URI savedCommentUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedComment.getId())
                .toUri();
        return ResponseEntity.created(savedCommentUri).body(savedComment);
    }

    @DeleteMapping("/delete-comment/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}