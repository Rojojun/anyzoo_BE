package com.finalproject.breeding.board.controller;

import com.finalproject.breeding.board.dto.CommunityRequestDto;
import com.finalproject.breeding.board.dto.CommunityResponseDto;
import com.finalproject.breeding.board.dto.PostRequestDto;
import com.finalproject.breeding.board.dto.PostResponseDto;
import com.finalproject.breeding.board.service.CommunityService;
import com.finalproject.breeding.etc.dto.StatusResponseDto;
import com.finalproject.breeding.user.User;
import com.finalproject.breeding.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class CommunityController {

    private final CommunityService communityService;
    private final UserService userService;

    @PostMapping("/api/community")
    public ResponseEntity<Object> registCommunity(@RequestBody CommunityRequestDto communityRequestDto){
        User user = userService.getUser();
        Map<String, Object> data = communityService.registCommunity(communityRequestDto, user);
        return new ResponseEntity<>(new StatusResponseDto("등록 되었습니다.", data), HttpStatus.OK);
    }



    @GetMapping("/api/community/{boardMainId}")
    public CommunityResponseDto getCommunityDetail(@PathVariable Long boardMainId){
        return communityService.getCommunityDetail(boardMainId);
    }

    // 전체 게시글 조회하기 (all, free, review, qna)
    @GetMapping("/api/community/category/{category}")
    public Slice<CommunityResponseDto> readAllCommunity(HttpServletRequest httpServletRequest, @PathVariable String category) {
        int page = Integer.parseInt(httpServletRequest.getParameter("page"));
        return communityService.readCommunity(page, category);
    }

    @DeleteMapping("/api/community/{boardMainId}")
    public ResponseEntity<Object> deleteCommunity(@PathVariable Long boardMainId){
        User user = userService.getUser();
        communityService.deleteCommunity(user, boardMainId);
        return new ResponseEntity<>(new StatusResponseDto("삭제 되었습니다.", ""), HttpStatus.OK);
    }

    @PatchMapping("/api/community/{boardMainId}")
    public ResponseEntity<Object> updateCommunity(@PathVariable Long boardMainId,
                                                  @RequestBody CommunityRequestDto communityRequestDto){
        User user = userService.getUser();
        Map<String, Object> data = communityService.updateCommunity(boardMainId, communityRequestDto, user);
        return new ResponseEntity<>(new StatusResponseDto("수정 되었습니다.", data), HttpStatus.OK);
    }

}
