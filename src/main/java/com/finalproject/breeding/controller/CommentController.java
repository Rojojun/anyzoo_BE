package com.finalproject.breeding.controller;

import com.finalproject.breeding.dto.CommentRequestDto;
import com.finalproject.breeding.model.Comment;
import com.finalproject.breeding.repository.BoardMainRepository;
import com.finalproject.breeding.repository.CommentRepository;
import com.finalproject.breeding.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentRepository commentRepository;

    private final CommentService commentService;
    

    //댓글 작성
    @PostMapping("/api/comment/{boardMainId}")
    public Comment createComment(@RequestBody CommentRequestDto requestDto,
                                 @PathVariable Long boardMainId,
                                 HttpServletRequest httpServletRequest
                                 ){
        Long userId = 1L; //현제 로그인한 유저 pk
    return commentService.createComment(requestDto, boardMainId, userId);
    }

    //댓글 삭제
    @DeleteMapping("/api/comment/{id}")
    public Long deleteComment(@PathVariable Long id, HttpServletRequest httpServletRequest){
        commentRepository.deleteById(id);
        return id;
    }

    //댓글 수정
    @PatchMapping ("/api/house/{id}")
    public Long patchComment(@RequestBody CommentRequestDto requestDto,
                             @PathVariable Long id,
                             HttpServletRequest httpServletRequest) {

        commentService.patchComment(requestDto,id);
            return id;
        }

    //댓글 불러오기
    @GetMapping("/api/comment/{boardMainId}")
    public List<Comment> getAllCommnet(@PathVariable Long boardMainId){
        List<Comment> comments= commentRepository.findAllByBoardMain_Id(boardMainId);
        return comments;
    }

}
