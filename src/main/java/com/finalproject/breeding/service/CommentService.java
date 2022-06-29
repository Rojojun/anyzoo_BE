package com.finalproject.breeding.service;


import com.finalproject.breeding.dto.CommentRequestDto;
import com.finalproject.breeding.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.finalproject.breeding.model.Comment;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    @Transactional
    public Comment createComment(CommentRequestDto requestDto, Long houseId) {

        Comment comment = new Comment(requestDto, houseId);
        commentRepository.save(comment);
        return comment;
    }
}
