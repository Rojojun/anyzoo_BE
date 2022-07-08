package com.finalproject.breeding.board.controller;

import com.finalproject.breeding.board.dto.CommunityRequestDto;
import com.finalproject.breeding.board.dto.CommunityResponseDto;
import com.finalproject.breeding.board.dto.PostRequestDto;
import com.finalproject.breeding.board.service.CommunityService;
import com.finalproject.breeding.etc.dto.StatusResponseDto;
import com.finalproject.breeding.user.User;
import com.finalproject.breeding.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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



    @GetMapping("/api/community/{communityId}")
    public CommunityResponseDto getCommunityDetail(@PathVariable Long communityId){
        return communityService.getCommunityDetail(communityId);
    }


}
