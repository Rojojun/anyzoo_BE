package com.finalproject.breeding.controller;

import com.finalproject.breeding.dto.PostRequestDto;
import com.finalproject.breeding.dto.PostResponseDto;
import com.finalproject.breeding.securityUtil.SecurityUtil;
import com.finalproject.breeding.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/api/post")
    public PostResponseDto registPost(@RequestBody PostRequestDto postRequestDto){
        String username = SecurityUtil.getCurrentUsername();
        return postService.registPost(postRequestDto, username);
    }

    @GetMapping("api/post")
}
