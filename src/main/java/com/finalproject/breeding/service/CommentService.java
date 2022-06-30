package com.finalproject.breeding.service;

import com.finalproject.breeding.dto.CommentRequestDto;
import com.finalproject.breeding.model.Comment;
import com.finalproject.breeding.model.User;
import com.finalproject.breeding.model.board.BoardMain;
import com.finalproject.breeding.repository.BoardMainRepository;
import com.finalproject.breeding.repository.CommentRepository;
import com.finalproject.breeding.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardMainRepository boardMainRepository;
    private final UserRepository userRepository;

    //댓글 작성
    @Transactional
    public Comment createComment( CommentRequestDto requestDto, Long boardMainId, Long userId) {

       BoardMain boardMain = boardMainRepository.findById(boardMainId).orElseThrow(
                ()-> new NullPointerException("게시글이 존재하지 않습니다."));

       User user = userRepository.findById(userId).orElseThrow(
                ()-> new NullPointerException("유저가 존재하지 않습니다."));

        Comment comment = new Comment(requestDto,boardMain,user);
        commentRepository.save(comment);
        return comment;
    }

    //댓글 수정
    @Transactional
    public void patchComment(CommentRequestDto requestDto, Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                ()-> new NullPointerException("댓글이 존재하지 않습니다."));


        comment.updateComment(requestDto);

    }


}
