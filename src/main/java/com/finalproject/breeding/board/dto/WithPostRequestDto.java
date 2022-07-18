package com.finalproject.breeding.board.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WithPostRequestDto {
    private String title;
    private String content;
    private String date;
    private Long limitPeople;
    private String location;
    private String name; //카테고리이름
}
