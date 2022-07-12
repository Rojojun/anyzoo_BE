package com.finalproject.breeding.etc.controller;


import com.finalproject.breeding.etc.dto.MyDto;
import com.finalproject.breeding.etc.dto.ReplyRequestDto;
import com.finalproject.breeding.etc.service.ReplyService;
import com.finalproject.breeding.user.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReplyController {
    private final ReplyService replyService;



    @PostMapping("/api/reply/{CommentId}")
    public ResponseEntity<MyDto> createReply(@RequestBody ReplyRequestDto requestDto,
                                             @PathVariable Long CommentId
    ){

        String username = SecurityUtil.getCurrentUsername(); //현제 로그인한 유저 pk
        return replyService.createReply(requestDto, CommentId, username);
    }
}
