package com.example.movies_api.mapper;

import com.example.movies_api.dto.CommentDto;
import com.example.movies_api.model.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentDtoMapper {
    public static CommentDto map(Comment comment) {
        CommentDto commentDto = new CommentDto();
        BeanUtils.copyProperties(comment, commentDto);
        return commentDto;
    }
}
