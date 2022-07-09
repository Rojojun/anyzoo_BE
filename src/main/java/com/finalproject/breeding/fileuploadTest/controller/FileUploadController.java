package com.finalproject.breeding.fileuploadTest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Multipart;

@RequiredArgsConstructor
@RestController
public class FileUploadController {
    private final FileUploadService fileUploadService;

    @PostMapping("/api/v1/upload")
    public String uploadVideo(@RequestPart MultipartFile file) {
        return fileUploadService.uploadVideo(file);
    }
}
