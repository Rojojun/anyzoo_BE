package com.finalproject.breeding.controller;

import com.finalproject.breeding.model.User;
import com.finalproject.breeding.repository.HeartMapping;
import com.finalproject.breeding.service.HeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HeartController {

    private final HeartService heartService;

    // 좋아요 등록, 취소
    @PostMapping("/api/heart/{boardMainId}")
    public void upHeart(@PathVariable Long boardMainId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User principal = (User) authentication.getPrincipal();
        String username = principal.getUsername();
        heartService.upHeart(boardMainId, username);
    }

    // 특정글 좋아요 체크
    @GetMapping("/api/heart/{boardMainId}")
    public boolean checkHeart(@PathVariable Long boardMainId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User principal = (User) authentication.getPrincipal();
        String username = principal.getUsername();
        return heartService.checkHeart(boardMainId, username);
    }

    // 좋아요 목록 체크
    @GetMapping("/api/heartCheck")
    public List<HeartMapping> getHeart(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User principal = (User) authentication.getPrincipal();
        String username = principal.getUsername();
        return heartService.getHeart(username);
    }
}
