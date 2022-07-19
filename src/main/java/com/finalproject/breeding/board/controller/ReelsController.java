package com.finalproject.breeding.board.controller;

import com.finalproject.breeding.board.dto.ReelsRequestDto;
import com.finalproject.breeding.board.dto.ReelsResponseDto;
import com.finalproject.breeding.etc.dto.StatusResponseDto;
import com.finalproject.breeding.board.service.ReelsService;
import com.finalproject.breeding.user.User;
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
public class ReelsController {
    private final UserService userService;
    private final ReelsService reelsService;
    // 릴스 게시글 등록 (동영상 및 썸네일 추출 값 받음)
    @PostMapping("/api/reels")
    public ResponseEntity<Object> regigstReels(@RequestBody ReelsRequestDto reelsRequestDto) {
        User user = userService.getUser();
        Map<String, Object> data = reelsService.registReels(reelsRequestDto, user);
        return new ResponseEntity<>(new StatusResponseDto("등록 되었습니다.", data ), HttpStatus.OK);
    }
    // 릴스 게시글 조회 카테고리를 사용하여 게시글을 조회하며 자세한 부분은 post와 동일
    @GetMapping("/api/reels/category/{category}")
    public Slice<ReelsResponseDto>  readAllReels(HttpServletRequest httpServletRequest, @PathVariable String category) {
        int page = Integer.parseInt(httpServletRequest.getParameter("page"));
        return reelsService.readCategoryReels(page, category);
    }
    // 릴스 상세 조회
    @GetMapping("/api/reels/{boardMainId}")
    public ReelsResponseDto getReelsDetail(@PathVariable Long boardMainId) {
        return reelsService.getReelsDetail(boardMainId);
    }
    // 릴스 특정 게시글 삭제
    @DeleteMapping("/api/reels/{boardMainId}")
    public ResponseEntity<Object> deleteReels(@PathVariable Long boardMainId) {
        User user = userService.getUser();
        reelsService.deleteReels(boardMainId, user);
        return new ResponseEntity<>(new StatusResponseDto("삭제 되었습니다.", ""), HttpStatus.OK);
    }
}