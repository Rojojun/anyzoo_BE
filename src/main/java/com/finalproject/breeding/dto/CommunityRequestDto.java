package com.finalproject.breeding.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommunityRequestDto {


    private String title;
    private String content;
    private String name; //카테고리이름

}
