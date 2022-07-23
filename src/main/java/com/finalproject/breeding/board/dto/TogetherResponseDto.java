package com.finalproject.breeding.board.dto;

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
    private LocalDateTime dateTime;
    private String nickname;
    private String userProfileImg;
    private List<TogetherImage> img;


}
