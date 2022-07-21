package com.finalproject.breeding.board.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TogetherRequestDto {

    private String title;
    private String content;
    private String categoryName;
    private String date;
    private int limitPeople;
    private Long provinceId;

}
