package com.finalproject.breeding.controller;

import com.finalproject.breeding.dto.ReelsRequestDto;
import com.finalproject.breeding.model.board.Reels;
import com.finalproject.breeding.service.ReelsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReelsController {
    private final ReelsService reelsService;

    @PostMapping("/api/reels")
    public Reels createReels(@RequestBody ReelsRequestDto requestDto){
        Long boardMainId = 1L;
        Long postCategoryId = 1L;
        return reelsService.createReels(requestDto, boardMainId, postCategoryId);
    }

}
