package com.finalproject.breeding.controller;

import com.finalproject.breeding.dto.PostRequestDto;
import com.finalproject.breeding.dto.PostResponseDto;
import com.finalproject.breeding.error.StatusResponseDto;
import com.finalproject.breeding.model.User;
import com.finalproject.breeding.repository.PostRepository;
import com.finalproject.breeding.service.PostService;
import com.finalproject.breeding.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final UserService userService;
    private final PostRepository postRepository;

    @PostMapping("/api/post")
    public ResponseEntity<Object> registPost(@RequestBody PostRequestDto postRequestDto){
        User user = userService.getUser();
        Map<String, Object> data = postService.registPost(postRequestDto, user);
        return new ResponseEntity<>(new StatusResponseDto("등록 되었습니다.", data), HttpStatus.OK);
    }

    // 카테고리로 게시글 조회하기 서비스 구현 예정,
    // 코드 상으로 남아있음 -> 주석처리함
//    @GetMapping("/api/post")
//    public List<PostListResponseDto> readAllPost(/*@RequestParam(required = false) String postCategory*/) {
//        return postService.readAllPost(/*postCategory*/);
//    }

    // 게시글 상세 조회
    @GetMapping("/api/post/{id}")
    public PostResponseDto getPostDetail(@PathVariable Long id) {
        return postService.getPostDetail(id);
    }

    @GetMapping("/api/post")
    public Slice<PostMapping> getAllPost(HttpServletRequest httpServletRequest){
        Long page = Long.parseLong(httpServletRequest.getParameter("page"));
        return postService.getAll(page);
    }
//
//    //게시글 삭제
//    @DeleteMapping("/api/post/{id}")
//    public void deletePost(@PathVariable Long id) {
//        String username = SecurityUtil.getCurrentUsername();
//        postService.deletePost(id, username);
//    }
//
//    // 게시글 수정
//    @PatchMapping("/api/post/{id}")
//    public void updatePost(@PathVariable Long id,
//                           @RequestBody PostRequest4EditDto requestDto) {
//        String username = SecurityUtil.getCurrentUsername();
//        postService.updatePost(id, requestDto,username);
//    }
}
