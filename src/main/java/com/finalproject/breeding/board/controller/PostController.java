package com.finalproject.breeding.board.controller;

import com.finalproject.breeding.board.dto.PostRequest4EditDto;
import com.finalproject.breeding.board.dto.PostRequestDto;
import com.finalproject.breeding.board.dto.PostResponseDto;
import com.finalproject.breeding.etc.dto.StatusResponseDto;
import com.finalproject.breeding.image.AwsS3Service;
import com.finalproject.breeding.user.SecurityUtil;
import com.finalproject.breeding.user.User;
import com.finalproject.breeding.board.service.PostService;
import com.finalproject.breeding.user.service.UserService;
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
    private final AwsS3Service awsS3Service;

    @PostMapping("/api/post")
    public ResponseEntity<Object> registPost(@RequestBody PostRequestDto postRequestDto){
        User user = userService.getUser();
        Map<String, Object> data = postService.registPost(postRequestDto, user);
        return new ResponseEntity<>(new StatusResponseDto("등록 되었습니다.", data), HttpStatus.OK);
    }

    // 카테고리로 게시글 조회하기 (all,cute,cool,pretty,comic, allLike, cuteLike, coolLike, prettyLike, comicLike)
    @GetMapping("/api/post/category/{category}")
    public Slice<PostResponseDto> readAllPost(HttpServletRequest httpServletRequest, @PathVariable String category) {
        Long page = Long.parseLong(httpServletRequest.getParameter("page"));
        return postService.readCategoryPost(page, category);
    }

    // 게시글 상세 조회
    @GetMapping("/api/post/{id}")
    public PostResponseDto getPostDetail(@PathVariable Long id) {
        return postService.getPostDetail(id);
    }


    //게시글 삭제
    @DeleteMapping("/api/post/{id}")
    public ResponseEntity<Object> deletePost(@PathVariable Long id) {
        User user = userService.getUser();
        awsS3Service.removePostImages(id);
        postService.deletePost(id, user);
        return new ResponseEntity<>(new StatusResponseDto("삭제 되었습니다.", "null"), HttpStatus.OK);
    }

    // 게시글 수정
    @PatchMapping("/api/post/{id}")
    public ResponseEntity<Object> updatePost(@PathVariable Long id,
                           @RequestBody PostRequest4EditDto requestDto) {
        User user = userService.getUser();
        Map<String, Object> data = postService.updatePost(id, requestDto, user);
        return new ResponseEntity<>(new StatusResponseDto("수정 되었습니다.", data), HttpStatus.OK);
    }
}
