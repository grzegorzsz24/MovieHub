package com.example.movies_api.repository;

import com.example.movies_api.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByUser_EmailAndMovie_Id(String userEmail, long movieId);
}
