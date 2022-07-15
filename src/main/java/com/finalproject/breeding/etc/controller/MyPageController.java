package com.finalproject.breeding.etc.controller;

import com.finalproject.breeding.board.model.BoardMain;
import com.finalproject.breeding.etc.dto.FollowResponseDto;
import com.finalproject.breeding.etc.dto.MyPagePostResponseDto;
import com.finalproject.breeding.etc.dto.StatusResponseDto;
import com.finalproject.breeding.etc.service.MyPageService;
import com.finalproject.breeding.user.User;
import com.finalproject.breeding.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class MyPageController {

    private final MyPageService myPageService;
    private final UserService userService;


    @GetMapping("/api/mypage/{userId}")
    public Slice<MyPagePostResponseDto> getMyPagePost(HttpServletRequest httpServletRequest, @PathVariable String nickname){
        int page = Integer.parseInt(httpServletRequest.getParameter("page"));
        return myPageService.getMyPagePost(nickname, page);
    }

//    @GetMapping("/api/mypage/heart")
//    public Slice<MyPagePostResponseDto> getMyPageHeart(HttpServletRequest httpServletRequest){
//
//    }

    @PostMapping("/api/follow/{nickname}")
    public ResponseEntity<Object> followUnFollow(@PathVariable String nickname){
        User follower = userService.getUser();
        Map<String, Object> data = myPageService.followUnFollow(follower, nickname);
        return new ResponseEntity<>(new StatusResponseDto("반영 되었습니다.", data), HttpStatus.OK);
    }


    // 팔로잉 조회
    @GetMapping("/api/following/{nickname}")
    public List<FollowResponseDto> getFollowing(@PathVariable String nickname){
        return myPageService.getFollowing(nickname);
    }

    // 팔로워 조회
    @GetMapping("/api/follower/{nickname}")
    public List<FollowResponseDto> getFollower(@PathVariable String nickname){
        return myPageService.getFollower(nickname);
    }

    @GetMapping("/api/follow/{nickname}")
    public boolean checkFollow(@PathVariable String nickname){
        User follower = userService.getUser();
        return myPageService.checkFollow(follower, nickname);
    }

}
