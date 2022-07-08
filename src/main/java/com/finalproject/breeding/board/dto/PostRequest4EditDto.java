package com.finalproject.breeding.board.dto;

import com.finalproject.breeding.image.model.PostImage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostRequest4EditDto {
    private String content;
    private List<PostImage> postImages;
}
