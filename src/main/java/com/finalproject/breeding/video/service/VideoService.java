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
    public VideoResponseDto videoSave(MultipartFile videoFile, String thumnailTime, String startPoint) throws Exception {
        // 비디오가 저장되지 않을 때 공백으로 표시
        String video = "";
        String tunmbnail = "";
        String message = "";
        String getThumnatiTime = thumnailTime;
        String getStartPoint = startPoint;
        // video 파일이 살제로 있을 경우 s3에 저장 후 URL반환
        if (videoFile != null) {
            video = s3VideoUploader.upload(videoFile, "reels", true, getThumnatiTime, getStartPoint);
            tunmbnail = s3VideoUploader.uploadThumbnail(videoFile, "thumbnail", true, getThumnatiTime );
            message = "동영상 및 썸네일 추출 성공";
        } else if (videoFile == null) {
            message = "동영상 업로드가 실패하였습니다. 동영상의 길이는 최소 4초이상 300초미만이여야하며, 동영상의 가로 길이는 300px보다 커야합니다.";
        }
        VideoResponseDto videoResponseDto = new VideoResponseDto(video ,tunmbnail, message);
        return videoResponseDto;
    }
}
