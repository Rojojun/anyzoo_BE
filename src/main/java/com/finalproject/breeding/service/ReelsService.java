package com.finalproject.breeding.service;

import com.finalproject.breeding.dto.ReelsRequestDto;
import com.finalproject.breeding.model.board.BoardMain;
import com.finalproject.breeding.model.board.Reels;
import com.finalproject.breeding.model.category.PostCategory;
import com.finalproject.breeding.repository.BoardMainRepository;
import com.finalproject.breeding.repository.PostCategoryRepository;
import com.finalproject.breeding.repository.ReelsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReelsService {

    private final ReelsRepository reelsRepository;
    private final BoardMainRepository boardMainRepository;
    private final PostCategoryRepository postCategoryRepository;

    //릴스 작성
    @Transactional
    public Reels createReels(ReelsRequestDto requestDto, Long boardMainId, Long postCategoryId) {

        BoardMain boardMain = boardMainRepository.findById(boardMainId).orElseThrow(
                ()-> new NullPointerException("게시글이 존재하지 않습니다."));

        PostCategory postCategory = postCategoryRepository.findById(postCategoryId).orElseThrow(
                ()-> new NullPointerException("유저가 존재하지 않습니다."));

        Reels reels = new Reels(requestDto, boardMain, postCategory);
        reelsRepository.save(reels);
        return reels;
    }
    //릴스 수정
    @Transactional
    public void patchReels(ReelsRequestDto requestDto, Long reelsId) {
        Reels reels = reelsRepository.findById(reelsId).orElseThrow(
                () -> new NullPointerException("릴스가 존재하지 않습니다."));

        reels.updateReels(requestDto);
    }

}
