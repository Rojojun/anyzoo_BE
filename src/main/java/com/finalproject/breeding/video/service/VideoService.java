package com.finalproject.breeding.video.service;

import com.finalproject.breeding.error.CustomException;
import com.finalproject.breeding.error.ErrorCode;
import com.finalproject.breeding.video.util.S3VideoUploader;
import com.finalproject.breeding.video.dto.VideoResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Locale;

@RequiredArgsConstructor
@Slf4j
@Service
public class VideoService {
    private final S3VideoUploader s3VideoUploader;
    public VideoResponseDto videoSave(MultipartFile videoFile, String thumbnailTime, String startPoint) throws Exception {
        // 비디오가 저장되지 않을 때 공백으로 표시
        String video = "";
        String tunmbnail = "";
        String getThumbnailTime = thumbnailTime;
        String getStartPoint = startPoint;

        String fileName = videoFile.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf("."));

        ArrayList<String> accessableLists = new ArrayList<>();

        accessableLists.add(".mp4");
        accessableLists.add(".avi");
        accessableLists.add(".mov");
        accessableLists.add(".mov");
        accessableLists.add(".MOV");
        accessableLists.add(".m4v");
        accessableLists.add(".wmv");
        accessableLists.add(".mkv");

        if (accessableLists.contains(ext)) {
            log.info(ext, "파일 확장자 유효성 검증에 성공하였습니다.");
        } else if (!accessableLists.contains(ext)) {
            log.error(ext, "500 ERORR : 동영상이 아니거나, 아직 지원하지 않는 확장 파일입니다.");
            throw new CustomException(ErrorCode.EXTRACTION_VALIDATION_ERROR);
        }
        // video 파일이 살제로 있을 경우 s3에 저장 후 URL반환
        if (videoFile != null) {
            video = s3VideoUploader.upload(videoFile, "reels", true, getThumbnailTime, getStartPoint);
            tunmbnail = s3VideoUploader.uploadThumbnail(videoFile, "thumbnail", true, getThumbnailTime );
            log.info("동영상 및 썸네일 추출 성공");
        }
        VideoResponseDto videoResponseDto = new VideoResponseDto(video ,tunmbnail);
        return videoResponseDto;
    }
}
