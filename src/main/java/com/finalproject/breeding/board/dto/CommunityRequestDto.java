package com.finalproject.breeding.board.dto;

import com.finalproject.breeding.etc.image.model.CommunityImage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CommunityRequestDto {


    private String title;
    private String content;
    private String categoryName; //카테고리이름
    private List<CommunityImage> communityImages;

}
