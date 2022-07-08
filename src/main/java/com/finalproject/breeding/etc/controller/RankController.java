package com.finalproject.breeding.etc.controller;

import com.finalproject.breeding.board.dto.PostResponseDto;
import com.finalproject.breeding.board.model.Post;
import com.finalproject.breeding.etc.service.RankService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class RankController {

    private final RankService rankService;


    @GetMapping("/api/rank/week/{category}") // 주간랭킹조회(현재날짜기준-6일) category = all, cute, cool, comic
    public List<PostResponseDto> getWeekPost(@PathVariable String category){
        return rankService.getWeekPost(category);
    }

    @GetMapping("/api/rank/day") // 일간랭킹 TOP2
    public List<PostResponseDto> getDayPost(){return rankService.getDayPost();}
}
