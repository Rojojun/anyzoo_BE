package com.finalproject.breeding.board.dto;

import com.finalproject.breeding.board.model.Together;
import com.finalproject.breeding.image.model.TogetherImage;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class TogetherResponseDto {
    private Long togetherId;
    private Long boardMainId;
    private String boardKind;
    private String cityName;
    private String provinceName;
    private String title;
    private String contents;
    private Long likeCnt;
    private LocalDateTime dday;
    private LocalDateTime dateTime;
    private String nickname;
    private String userProfileImg;
    private List<TogetherImage> img;

    public TogetherResponseDto(Together together){
        this.togetherId = together.getId();
        this.boardMainId = together.getBoardMain().getId();
        this.boardKind = together.getBoardMain().getBoardKind().name();
        this.cityName = together.getProvinceAreas().getCityAreas().getName();
        this.provinceName = together.getProvinceAreas().getName();
        this.title = together.getTitle();
        this.contents = together.getBoardMain().getContent();
        this.likeCnt = together.getBoardMain().getLikeCnt();
        this.dday = together.getDday();
        this.dateTime = together.getBoardMain().getCreatedAt();
        this.nickname = together.getUser().getNickname();
        this.userProfileImg = together.getUser().getUserImage().getUrl();
        this.img = together.getTogetherImages();
    }

}
