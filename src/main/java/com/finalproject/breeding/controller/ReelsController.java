package com.finalproject.breeding.controller;

import com.finalproject.breeding.dto.ReelsRequestDto;
import com.finalproject.breeding.model.board.Reels;
import com.finalproject.breeding.repository.ReelsRepository;
import com.finalproject.breeding.service.ReelsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ReelsController {
    private final ReelsService reelsService;
    private final ReelsRepository reelsRepository;

    //릴스 작성
    @PostMapping("/api/reels")
    public Reels createReels(@RequestBody ReelsRequestDto requestDto){
        Long boardMainId = 1L;
        Long postCategoryId = 1L;
        return reelsService.createReels(requestDto, boardMainId, postCategoryId);
    }

    //릴스 전체 조회
    @GetMapping("/api/reels")
    public List<Reels> getAllReels(){
        List<Reels> reels= reelsRepository.findAll();
        return reels;
    }

    //특정 릴스 조회
    @GetMapping("/api/reels/{reelsId}")
    public Optional<Reels> getReels(@PathVariable Long reelsId){
        Optional<Reels> reels = reelsRepository.findById(reelsId);
        return reels;
    }



    //릴스 삭제
    @DeleteMapping("/api/reels/edit/{reelsId}")
    public Long deleteReels(@PathVariable Long reelsId, HttpServletRequest httpServletRequest){
        reelsRepository.deleteById(reelsId);
        return reelsId;
    }

    //릴스 수정
    @PatchMapping ("/api/reels/edit/{reelsId}")
    public Long patchReels(@RequestBody ReelsRequestDto requestDto,
                             @PathVariable Long reelsId,
                             HttpServletRequest httpServletRequest) {

        reelsService.patchReels(requestDto,reelsId);
        return reelsId;
    }

}
