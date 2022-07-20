package com.finalproject.breeding.board.dto;

import com.finalproject.breeding.image.model.PostImage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bytedeco.opencv.presets.opencv_core;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostRequest4EditDto {
    private String content;
    private LocalDateTime dateTime;
    private String categoryName;
    private List<PostImage> postImages;
}
