package com.finalproject.breeding.etc.controller;

import com.finalproject.breeding.board.dto.CommunityResponseDto;
import com.finalproject.breeding.board.dto.TogetherResponseDto;
import com.finalproject.breeding.etc.dto.response.*;
import com.finalproject.breeding.etc.service.MyPageService;
import com.finalproject.breeding.user.User;
import com.finalproject.breeding.user.dto.responseDto.UserInfo;
import com.finalproject.breeding.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class MyPageController {

    private final MyPageService myPageService;
    private final UserService userService;

    @GetMapping("/api/mypage/userInfo/{nickname}")
    public UserInfo getUserInfo(@PathVariable String nickname){
        return myPageService.getUserInfo(nickname);
    }

    @GetMapping("/api/mypage/post/{nickname}")
    public Slice<MyPagePostDto> getMyPagePost(HttpServletRequest httpServletRequest, @PathVariable String nickname){
        int page = Integer.parseInt(httpServletRequest.getParameter("page"));
        return myPageService.getMyPagePost(nickname, page);
    }

    @GetMapping("/api/mypage/reels/{nickname}")
    public Slice<MyPageReelsDto> getMyPageReels(HttpServletRequest httpServletRequest, @PathVariable String nickname){
        int page = Integer.parseInt(httpServletRequest.getParameter("page"));
        return myPageService.getMyPageReels(nickname, page);
    }

    @GetMapping("/api/mypage/community/{nickname}")
    public Slice<CommunityResponseDto> getMyPageCommunity(HttpServletRequest httpServletRequest, @PathVariable String nickname){
        int page = Integer.parseInt(httpServletRequest.getParameter("page"));
        return myPageService.getMyPageCommunity(nickname,page);
    }
    @GetMapping("/api/mypage/together/{nickname}")
    public Slice<TogetherResponseDto> getMyPageTogether(HttpServletRequest httpServletRequest, @PathVariable String nickname){
        int page = Integer.parseInt(httpServletRequest.getParameter("page"));
        return myPageService.getMyPageTogether(nickname,page);
    }


    @PostMapping("/api/follow/{nickname}")
    public ResponseEntity<Object> followUnFollow(@PathVariable String nickname){
        User follower = userService.getUser();
        Map<String, Object> data = myPageService.followUnFollow(follower, nickname);
        return new ResponseEntity<>(new StatusResponseDto("반영 되었습니다.", data), HttpStatus.OK);
    }


    // 팔로잉 조회
    @GetMapping("/api/following/{nickname}")
    public List<FollowingDto> getFollowing(@PathVariable String nickname){
        return myPageService.getFollowing(nickname);
    }

    // 팔로워 조회
    @GetMapping("/api/follower/{nickname}")
    public List<FollowerDto> getFollower(@PathVariable String nickname){
        return myPageService.getFollower(nickname);
    }

    @GetMapping("/api/follow/{nickname}")
    public boolean checkFollow(@PathVariable String nickname){
        User follower = userService.getUser();
        return myPageService.checkFollow(follower, nickname);
    }

}
