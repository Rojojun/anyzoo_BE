package com.finalproject.breeding.fileUploadTest.controller;

import com.finalproject.breeding.fileUploadTest.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;

@RequiredArgsConstructor
@RestController
public class HelloController {

    private final S3Uploader s3Uploader;

    @PostMapping("/reels/test")
    public String upload(@RequestParam(value = "file", required = false) MultipartFile multipartFile) throws IOException {
        s3Uploader.upload(multipartFile, "reels");
        return "test";
    }
    @PostMapping("/thumbnail/export")
    public HashMap<String, Object> exportThumbnail
}