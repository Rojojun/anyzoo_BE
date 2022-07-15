package com.finalproject.breeding.etc.controller;


import com.finalproject.breeding.board.model.BoardMain;
import com.finalproject.breeding.board.repository.BoardMainRepository;
import com.finalproject.breeding.dto.CommentResponseDto;
import com.finalproject.breeding.error.ErrorCode;
import com.finalproject.breeding.etc.dto.CommentRequestDto;
import com.finalproject.breeding.etc.dto.MyDto;
import com.finalproject.breeding.etc.dto.ReplyMapping;
import com.finalproject.breeding.etc.dto.ReplyRequestDto;
import com.finalproject.breeding.etc.model.Comment;
import com.finalproject.breeding.etc.model.Reply;
import com.finalproject.breeding.etc.repository.ReplyRepository;
import com.finalproject.breeding.etc.service.ReplyService;
import com.finalproject.breeding.user.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ReplyController {
    private final ReplyService replyService;
    private final ReplyRepository replyRepository;

    private final BoardMainRepository boardMainRepository;



    //대댓글 작성
    @PostMapping("/api/reply/{boardMainId}/{CommentId}")
    public ResponseEntity<MyDto> createReply(@RequestBody ReplyRequestDto requestDto,
                                             @PathVariable Long boardMainId,
                                             @PathVariable Long CommentId
    ){String username = SecurityUtil.getCurrentUsername(); //현제 로그인한 유저 pk
        return replyService.createReply(requestDto,boardMainId, CommentId, username);
    }

    //대댓글 삭제
    @DeleteMapping("/api/reply/edit/{boardMainId}/{replyId}")
    public ResponseEntity<MyDto> deleteComment(@PathVariable Long replyId,@PathVariable Long boardMainId){

        BoardMain boardMain = boardMainRepository.findById(boardMainId).orElseThrow(
                () -> new NullPointerException("게시글이 존재하지 않습니다."));


        MyDto dto = new MyDto();
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        String userId = SecurityUtil.getCurrentUsername(); //현제 로그인한 유저 pk
        Optional<Reply> a =replyRepository.findById(replyId);
        String userId1 =a.get().getUser().getUsername();

        if (Objects.equals(userId, userId1)) {   //댓글의 닉네임와 일치한다면

            boardMain.minusCommentCnt(boardMain);
            replyRepository.deleteById(replyId);
            dto.setStatus(ErrorCode.OK);
            dto.setData("replyId :"+replyId);
            dto.setMessage("댓글 삭제!");
            return new ResponseEntity<>(dto, header, HttpStatus.OK);
        }else{
            dto.setStatus(ErrorCode.COMMENT_WRONG_INPUT);
            dto.setData("replyId :"+replyId);
            dto.setMessage("사용자의 댓글이 아닙니다!");
            return new ResponseEntity<>(dto,header, HttpStatus.BAD_REQUEST);
        }
    }

    //댓글 수정
    @PatchMapping ("/api/reply/edit/{replyId}")
    public ResponseEntity<MyDto> patchComment(@RequestBody ReplyRequestDto requestDto,
                                              @PathVariable Long replyId) {
        MyDto dto = new MyDto();
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        String userId = SecurityUtil.getCurrentUsername(); //현제 로그인한 유저 pk
        Optional<Reply> a =replyRepository.findById(replyId);
        String userId1 =a.get().getUser().getUsername();

        if (Objects.equals(userId, userId1)) {   //댓글의 닉네임와 일치한다면

            replyService.patchReply(requestDto,replyId);
            dto.setStatus(ErrorCode.OK);
            dto.setData("commentId :"+replyId);
            dto.setMessage("대댓글 수정!");
            return new ResponseEntity<>(dto, header, HttpStatus.OK);
        }else{
            dto.setStatus(ErrorCode.COMMENT_WRONG_INPUT);
            dto.setData("replyId :"+replyId);
            dto.setMessage("사용자의 대댓글이 아닙니다!");
            return new ResponseEntity<>(dto,header, HttpStatus.BAD_REQUEST);
        }

    }

    //대댓글 불러오기
    @GetMapping("/api/reply/{commentId}")
    public List<ReplyMapping> getAllReply(@PathVariable Long commentId){
        List<ReplyMapping> replies =  replyRepository.findByCommentId(commentId);
        return replies;

    }


}
