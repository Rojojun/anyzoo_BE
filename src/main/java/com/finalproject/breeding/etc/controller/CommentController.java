package com.finalproject.breeding.etc.controller;

import com.finalproject.breeding.board.model.BoardMain;
import com.finalproject.breeding.board.repository.BoardMainRepository;
import com.finalproject.breeding.etc.dto.CommentRequestDto;
import com.finalproject.breeding.etc.dto.MyDto;
import com.finalproject.breeding.error.ErrorCode;
import com.finalproject.breeding.etc.model.Comment;
import com.finalproject.breeding.etc.repository.CommentRepository;
import com.finalproject.breeding.etc.service.CommentService;
import com.finalproject.breeding.user.security.SecurityUtil;
import com.finalproject.breeding.dto.CommentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final BoardMainRepository boardMainRepository;

    private final CommentService commentService;


    //댓글 작성
    @PostMapping("/api/comment/{boardMainId}")
    public ResponseEntity<MyDto> createComment(@RequestBody CommentRequestDto requestDto, @PathVariable Long boardMainId){
        String username = SecurityUtil.getCurrentUsername(); //현제 로그인한 유저 pk
        return commentService.createComment(requestDto, boardMainId, username);
    }

    //댓글 삭제
    @DeleteMapping("/api/comment/edit/{commentId}")
    public ResponseEntity<MyDto> deleteComment( @PathVariable Long commentId){
        return commentService.deleteComment(commentId);
    }

    //댓글 수정
    @PatchMapping ("/api/comment/edit/{commentId}")
    public ResponseEntity<MyDto> patchComment(@RequestBody CommentRequestDto requestDto,
                                              @PathVariable Long commentId) {
        return commentService.patchComment(requestDto, commentId);
    }

    //댓글 불러오기
    @GetMapping("/api/comment/{boardMainId}")
    public CommentResponseDto getAllCommnet(@PathVariable Long boardMainId, HttpServletRequest httpServletRequest){
        Long page = Long.parseLong(httpServletRequest.getParameter("page"));
        return commentService.getAllCommnet(boardMainId, page);

    }

    //댓글 수 불러오기
    @GetMapping("/api/comment/count/{boardMainId}")
    public Long getCommnetCount(@PathVariable Long boardMainId){
        BoardMain boardMain = boardMainRepository.findById(boardMainId).orElseThrow(
                () -> new NullPointerException("게시글이 존재하지 않습니다."));
        return boardMain.getCommentCnt();

    }


}
