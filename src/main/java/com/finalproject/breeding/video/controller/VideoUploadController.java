package com.finalproject.breeding.video.controller;

import com.finalproject.breeding.video.dto.VideoResponseDto;
import com.finalproject.breeding.video.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class VideoUploadController {
    private final VideoService videoService;
    @PostMapping("/api/upload")
    public VideoResponseDto videoURL(@RequestPart(name = "video",required = false) MultipartFile videoFile) throws Exception
    {
        return videoService.videoSave(videoFile);
    }
}
