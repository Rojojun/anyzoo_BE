package com.finalproject.breeding.video.service;

import com.finalproject.breeding.util.S3VideoUploader;
import com.finalproject.breeding.video.dto.VideoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class VideoService {
    private final S3VideoUploader s3VideoUploader;
    public VideoResponseDto videoSave(MultipartFile videoFile) throws Exception {
        // 비디오가 저장되지 않을 때 공백으로 표시
        String video = "";
        String tunmbnail = "";
        // video 파일이 살제로 있을 경우 s3에 저장 후 URL반환
        if (videoFile != null) {
            video = s3VideoUploader.upload(videoFile, "reels", true);
        }
        if (videoFile != null) {
            tunmbnail = s3VideoUploader.upload(videoFile, "thumbnail", true);
        }
        VideoResponseDto videoResponseDto = new VideoResponseDto(video ,tunmbnail);
        return videoResponseDto;
    }
}
