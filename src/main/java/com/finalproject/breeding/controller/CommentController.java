package com.finalproject.breeding.controller;

import com.finalproject.breeding.dto.CommentRequestDto;
import com.finalproject.breeding.model.Comment;
import com.finalproject.breeding.repository.CommentRepository;
import com.finalproject.breeding.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentRepository commentRepository;

    private final CommentService commentService;

    //댓글 작성
    @PostMapping("/api/comment/{boardMainId}")
    public Comment createComment(@PathVariable Long boardMainId, @RequestBody CommentRequestDto requestDto) {


    return commentService.createComment(requestDto, boardMainId);
    }

}
