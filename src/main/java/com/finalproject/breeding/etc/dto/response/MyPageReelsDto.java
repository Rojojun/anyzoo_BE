package com.finalproject.breeding.etc.dto.response;

import com.finalproject.breeding.board.model.Reels;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MyPageReelsDto {

    private Long boardMainId;
    private String titleImg;

    public MyPageReelsDto(Reels reels){
        this.boardMainId = reels.getBoardMain().getId();
        this.titleImg = reels.getTitleImg();
    }
}
