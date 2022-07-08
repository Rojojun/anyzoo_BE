package com.finalproject.breeding.etc.controller;

import com.finalproject.breeding.etc.dto.StatusResponseDto;
import com.finalproject.breeding.user.User;
import com.finalproject.breeding.etc.repository.HeartMapping;
import com.finalproject.breeding.etc.service.HeartService;
import com.finalproject.breeding.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class HeartController {

    private final HeartService heartService;
    private final UserService userService;

    // 좋아요 등록, 취소
    @PostMapping("/api/heart/{boardMainId}")
    public ResponseEntity<Object> upHeart(@PathVariable Long boardMainId){
        User user = userService.getUser();
        Map<String, Object> data = heartService.upHeart(boardMainId, user);
        return new ResponseEntity<>(new StatusResponseDto("좋아요 반영 되었습니다.", data), HttpStatus.OK);
    }

    // 특정글 좋아요 체크
    @GetMapping("/api/heart/{boardMainId}")
    public boolean checkHeart(@PathVariable Long boardMainId){
        User user = userService.getUser();
        return heartService.checkHeart(boardMainId, user);
    }

    // 좋아요 목록 체크
    @GetMapping("/api/heartCheck")
    public List<HeartMapping> getHeart(){
        User user = userService.getUser();
        return heartService.getHeart(user);
    }
}
