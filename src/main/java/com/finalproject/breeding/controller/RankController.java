package com.finalproject.breeding.controller;

import com.finalproject.breeding.model.board.Post;
import com.finalproject.breeding.service.RankService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class RankController {

    private final RankService rankService;

    @GetMapping("/api/rank/week")
    public List<Post> getWeekPost(){
        return rankService.getWeekPost();
    }

    @GetMapping("/api/rank/now")
    public List<Post> getNowPost(){return rankService.getNowPost();}
}
