package com.finalproject.breeding.controller;

import com.finalproject.breeding.repository.HeartMapping;
import com.finalproject.breeding.service.HeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class HeartController {

    private final HeartService heartService;

    // 좋아요 등록, 취소
    @PostMapping("/api/heart/{boardMainId}")
    public void upHeart(@PathVariable Long boardMainId){

        heartService.upHeart(boardMainId, userId);
    }

    // 특정글 좋아요 체크
    @GetMapping("/api/heart/{boardMainId}")
    public boolean checkHeart(HttpServletRequest httpServletRequest,
                              @PathVariable Long boardMainId){
        // 토큰에서 유저ID 가져오기
        return heartService.checkHeart(boardMainId, userId);
    }

    // 좋아요 목록 체크
    @GetMapping("/api/heartCheck")
    public List<HeartMapping> getHeart(HttpServletRequest httpServletRequest){
        // 토큰에서 유저ID 가져오기
        return heartService.getHeart(userId);
    }
}
