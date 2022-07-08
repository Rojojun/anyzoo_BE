package com.finalproject.breeding.board.dto;

import com.finalproject.breeding.image.model.PostImage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class PostRequestDto {

    private String content;
    private LocalDateTime dateTime;
    private String categoryName; //카테고리이름
    private List<PostImage> postImages;

}
