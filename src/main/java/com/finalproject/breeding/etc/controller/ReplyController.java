package com.finalproject.breeding.etc.controller;

import com.finalproject.breeding.etc.dto.MyDto;
import com.finalproject.breeding.etc.dto.ReplyMapping;
import com.finalproject.breeding.etc.dto.ReplyRequestDto;
import com.finalproject.breeding.etc.repository.ReplyRepository;
import com.finalproject.breeding.etc.service.ReplyService;
import com.finalproject.breeding.user.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;
    private final ReplyRepository replyRepository;
    //대댓글 작성
    @PostMapping("/api/reply/{commentId}")
    public ResponseEntity<MyDto> createReply(@RequestBody ReplyRequestDto requestDto, @PathVariable Long commentId){
        String username = SecurityUtil.getCurrentUsername(); //현제 로그인한 유저 pk
        return replyService.createReply(requestDto, commentId, username);
    }

    //대댓글 삭제
    @DeleteMapping("/api/reply/edit/{replyId}")
    public ResponseEntity<MyDto> deleteReply(@PathVariable Long replyId){
        return replyService.deleteReply(replyId);
    }

    //댓글 수정
    @PatchMapping ("/api/reply/edit/{replyId}")
    public ResponseEntity<MyDto> patchReply(@RequestBody ReplyRequestDto requestDto,
                                              @PathVariable Long replyId) {
        return replyService.patchReply(requestDto, replyId);
    }

    //대댓글 모두 불러오기
    @GetMapping("/api/reply/{commentId}")
    public List<ReplyMapping> getAllReply(@PathVariable Long commentId){
        return replyService.getAllReply(commentId);

    }

    //대댓글 수 불러오기
    @GetMapping("/api/reply/count/{commentId}")
    public Long getCommnetCount(@PathVariable Long commentId){
        return (long)replyRepository.findAllByCommentId(commentId).size();
    }
}
