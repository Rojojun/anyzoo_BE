package com.finalproject.breeding.board.controller;

import com.finalproject.breeding.board.dto.ReelsRequestDto;
import com.finalproject.breeding.etc.dto.StatusResponseDto;
import com.finalproject.breeding.board.service.ReelsService;
import com.finalproject.breeding.user.User;
import com.finalproject.breeding.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ReelsController {
    private final UserService userService;
    private final ReelsService reelsService;
    @PostMapping("/api/reels")
    public ResponseEntity<Object> regigstReels(@RequestBody ReelsRequestDto reelsRequestDto) {
        User user = userService.getUser();
        Map<String, Object> data = reelsService.registReels(reelsRequestDto, user);
        return new ResponseEntity<>(new StatusResponseDto("등록 되었습니다.", data ), HttpStatus.OK);
    }
}