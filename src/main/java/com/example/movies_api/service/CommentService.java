package com.example.movies_api.service;

import com.example.movies_api.dto.CommentDto;
import com.example.movies_api.mapper.CommentDtoMapper;
import com.example.movies_api.model.Comment;
import com.example.movies_api.model.Movie;
import com.example.movies_api.model.User;
import com.example.movies_api.repository.CommentRepository;
import com.example.movies_api.repository.MovieRepository;
import com.example.movies_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    public CommentDto addOrUpdateComment(String userEmail, long movieId, String commentContent) {
        Comment commentToSaveOrUpdate = commentRepository.findByUser_EmailAndMovie_Id(userEmail, movieId)
                .orElseGet(Comment::new);
        User user = userRepository.findByEmail(userEmail).orElseThrow();
        Movie movie = movieRepository.findById(movieId).orElseThrow();
        commentToSaveOrUpdate.setUser(user);
        commentToSaveOrUpdate.setMovie(movie);
        commentToSaveOrUpdate.setContent(commentContent);
        commentRepository.save(commentToSaveOrUpdate);
        return CommentDtoMapper.map(commentToSaveOrUpdate);
    }

    public void deleteComment(long id) {
        commentRepository.deleteById(id);
    }
}