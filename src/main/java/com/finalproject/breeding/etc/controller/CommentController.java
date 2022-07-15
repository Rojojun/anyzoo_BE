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
import com.finalproject.breeding.etc.dto.CommentResponseDto;
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

    private final CommentRepository commentRepository;

    private final BoardMainRepository boardMainRepository;

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
    @DeleteMapping("/api/comment/edit/{boardMainId}/{commentId}")
    public ResponseEntity<MyDto> deleteComment(@PathVariable Long commentId, @PathVariable Long boardMainId){

        BoardMain boardMain = boardMainRepository.findById(boardMainId).orElseThrow(
                () -> new NullPointerException("게시글이 존재하지 않습니다."));

        MyDto dto = new MyDto();
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        String userId = SecurityUtil.getCurrentUsername(); //현제 로그인한 유저 pk
        Optional<Comment> a =commentRepository.findById(commentId);
        String userId1 =a.get().getUser().getUsername();

        if (Objects.equals(userId, userId1)) {   //댓글의 닉네임와 일치한다면

            boardMain.minusCommentCnt(boardMain);
            commentRepository.deleteById(commentId);

            dto.setStatus(ErrorCode.OK);
            dto.setData("commentId :"+commentId);
            dto.setMessage("댓글 삭제!");
            return new ResponseEntity<>(dto, header, HttpStatus.OK);
        }else{
            dto.setStatus(ErrorCode.COMMENT_WRONG_INPUT);
            dto.setData("commentId :"+commentId);
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
            dto.setStatus(ErrorCode.OK);
            dto.setData("commentId :"+commentId);
            dto.setMessage("댓글 수정!");
            return new ResponseEntity<>(dto, header, HttpStatus.OK);
        }else{
            dto.setStatus(ErrorCode.COMMENT_WRONG_INPUT);
            dto.setData("commentId :"+commentId);
            dto.setMessage("사용자의 댓글이 아닙니다!");
            return new ResponseEntity<>(dto,header, HttpStatus.BAD_REQUEST);
        }

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
