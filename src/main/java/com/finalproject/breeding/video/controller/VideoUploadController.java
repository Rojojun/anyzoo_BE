package com.finalproject.breeding.video.controller;

import com.finalproject.breeding.video.dto.VideoResponseDto;
import com.finalproject.breeding.video.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


@RequiredArgsConstructor
@RestController
public class VideoUploadController {
    private final VideoService videoService;
    @PostMapping("/api/upload")
    public ResponseEntity<VideoResponseDto> videoURL(@RequestPart(name = "video",required = false) MultipartFile videoFile,
                                     @RequestParam String thumbnailTime, String startPoint) throws Exception {
        return ResponseEntity.ok(videoService.videoSave(videoFile, thumbnailTime, startPoint));

    }
}
