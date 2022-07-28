package com.finalproject.breeding.video.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.StringReader;

@Getter
@NoArgsConstructor
public class VideoResponseDto {
    private String video;
    private String thumbnail;
    private String message;
    public VideoResponseDto(String video, String thumbnail, String message) {
        this.video = video;
        this.thumbnail = thumbnail;
        this.message = message;
    }
}
