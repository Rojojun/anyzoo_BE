package com.finalproject.breeding.board.dto;

import com.finalproject.breeding.image.model.PostImage;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class PostRequestDto {

    private String content;
    private LocalDateTime dateTime;
    private String categoryName; //카테고리이름
    private List<PostImage> postImages;

}
