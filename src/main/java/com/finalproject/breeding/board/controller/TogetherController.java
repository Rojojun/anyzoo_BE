package com.finalproject.breeding.board.controller;

import com.finalproject.breeding.board.dto.TogetherRequestDto;
import com.finalproject.breeding.board.service.TogetherService;
import com.finalproject.breeding.etc.dto.StatusResponseDto;
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
public class TogetherController {

    private final TogetherService togetherService;
    private final UserService userService;

    @PostMapping("/api/together")
    public ResponseEntity<Object> registTogether(@RequestBody TogetherRequestDto togetherRequestDto){
        User user = userService.getUser();
        Map<String, Object> data = togetherService.registTogether(togetherRequestDto, user);
        return new ResponseEntity<>(new StatusResponseDto("등록 되었습니다.", data), HttpStatus.OK);
    }




}
