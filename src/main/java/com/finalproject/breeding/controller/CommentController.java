package com.finalproject.breeding.controller;

import com.finalproject.breeding.dto.CommentRequestDto;
import com.finalproject.breeding.dto.MyDto;
import com.finalproject.breeding.exception.StatusEnum;
import com.finalproject.breeding.model.Comment;
import com.finalproject.breeding.repository.CommentRepository;
import com.finalproject.breeding.securityUtil.SecurityUtil;
import com.finalproject.breeding.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentRepository commentRepository;

    private final CommentService commentService;


    //댓글 작성
    @PostMapping("/api/comment/{boardMainId}")
    public ResponseEntity<MyDto> createComment(@RequestBody CommentRequestDto requestDto,
                                 @PathVariable Long boardMainId
                                 ){

        String username = SecurityUtil.getCurrentUsername(); //현제 로그인한 유저 pk
    return commentService.createComment(requestDto, boardMainId, username);
    }

    //댓글 삭제
    @DeleteMapping("/api/comment/edit/{commentId}")
    public ResponseEntity<MyDto> deleteComment(@PathVariable Long commentId){

        MyDto dto = new MyDto();
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        String userId = SecurityUtil.getCurrentUsername(); //현제 로그인한 유저 pk
        Optional<Comment> a =commentRepository.findById(commentId);
        String userId1 =a.get().getUser().getUsername();

        if (Objects.equals(userId, userId1)) {   //댓글의 닉네임와 일치한다면
            commentRepository.deleteById(commentId);
            dto.setStatus(StatusEnum.OK);
            dto.setData(commentId);
            dto.setMessage("댓글 삭제!");
            return new ResponseEntity<>(dto, header, HttpStatus.OK);
        }else{
            dto.setStatus(StatusEnum.BAD_REQUEST);
            dto.setData(commentId);
            dto.setMessage("사용자의 댓글이 아닙니다!");
            return new ResponseEntity<>(dto,header, HttpStatus.BAD_REQUEST);
        }
    }

    //댓글 수정
    @PatchMapping ("/api/comment/edit/{commentId}")
    public ResponseEntity<MyDto> patchComment(@RequestBody CommentRequestDto requestDto,
                             @PathVariable Long commentId) {
        MyDto dto = new MyDto();
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        String userId = SecurityUtil.getCurrentUsername(); //현제 로그인한 유저 pk
        Optional<Comment> a =commentRepository.findById(commentId);
        String userId1 =a.get().getUser().getUsername();

        if (Objects.equals(userId, userId1)) {   //댓글의 닉네임와 일치한다면

            commentService.patchComment(requestDto,commentId);
            dto.setStatus(StatusEnum.OK);
            dto.setData(commentId);
            dto.setMessage("댓글 수정!");
            return new ResponseEntity<>(dto, header, HttpStatus.OK);
        }else{
            dto.setStatus(StatusEnum.BAD_REQUEST);
            dto.setData(commentId);
            dto.setMessage("사용자의 댓글이 아닙니다!");
            return new ResponseEntity<>(dto,header, HttpStatus.BAD_REQUEST);
        }

        }

    //댓글 불러오기
    @GetMapping("/api/comment/{boardMainId}")
    public List<Comment> getAllCommnet(@PathVariable Long boardMainId){
        List<Comment> comments= commentRepository.findAllByBoardMain_Id(boardMainId);
        return comments;
    }

}
