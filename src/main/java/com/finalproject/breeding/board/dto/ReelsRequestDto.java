package com.finalproject.breeding.board.dto;

import com.finalproject.breeding.video.model.ReelsThumbnail;
import com.finalproject.breeding.video.model.ReelsVideo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReelsRequestDto {

    private String content;
    private ReelsVideo video;
    private ReelsThumbnail titleImg;
    private String categoryName;
}