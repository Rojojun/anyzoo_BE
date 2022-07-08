package com.finalproject.breeding.controller;

import com.amazonaws.Response;
import com.finalproject.breeding.dto.ReelsRequestDto;
import com.finalproject.breeding.error.StatusResponseDto;
import com.finalproject.breeding.model.User;
import com.finalproject.breeding.model.board.Reels;
import com.finalproject.breeding.service.ReelsService;
import com.finalproject.breeding.service.UserService;
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