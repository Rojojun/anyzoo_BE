package com.finalproject.breeding.controller;

import com.finalproject.breeding.dto.PostListResponseDto;
import com.finalproject.breeding.dto.PostRequest4EditDto;
import com.finalproject.breeding.dto.PostRequestDto;
import com.finalproject.breeding.dto.PostResponseDto;
import com.finalproject.breeding.securityUtil.SecurityUtil;
import com.finalproject.breeding.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/api/post")
    public PostResponseDto registPost(@RequestBody PostRequestDto postRequestDto){
        String username = SecurityUtil.getCurrentUsername();
        return postService.registPost(postRequestDto, username);
    }

    // 카테고리로 게시글 조회하기 서비스 구현 예정,
    // 코드 상으로 남아있음 -> 주석처리함
    @GetMapping("/api/post")
    public List<PostListResponseDto> readAllPost(/*@RequestParam(required = false) String postCategory*/) {
        return postService.readAllPost(/*postCategory*/);
    }

    // 게시글 상세 조회
    @GetMapping("/api/post/{id}")
    public PostResponseDto readPostDetail(@PathVariable Long id) {
        return postService.getPostDetail(id);
    }

    //게시글 삭제
    @DeleteMapping("/api/post/{id}")
    public void deletePost(@PathVariable Long id) {
        String username = SecurityUtil.getCurrentUsername();
        postService.deletePost(id, username);
    }

    // 게시글 수정
    @PatchMapping("/api/post/{id}")
    public void updatePost(@PathVariable Long id,
                           @RequestBody PostRequest4EditDto requestDto) {
        String username = SecurityUtil.getCurrentUsername();
        postService.updatePost(id, requestDto,username);
    }
}
