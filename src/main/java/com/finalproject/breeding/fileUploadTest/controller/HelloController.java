package com.finalproject.breeding.fileUploadTest.controller;

import com.finalproject.breeding.fileUploadTest.service.S3Uploader;
import com.finalproject.breeding.fileUploadTest.service.ThumbnailService;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;

@RequiredArgsConstructor
@RestController
public class HelloController {
    private final S3Uploader s3Uploader;

    @PostMapping("/reels/test")
    public String upload(@RequestParam(value = "file", required = false) MultipartFile multipartFile) throws IOException {
        s3Uploader.upload(multipartFile, "reels");
        return "test";
    }
/*    @PostMapping("/thumbnail/export")
    public HashMap<String, Object> exportThumbnail(@RequestParam(value = "file", required = false) MultipartFile multipartFile) throws IOException {
        logger.info("Request url: /thumbnail/export");

        // MultipartFile -> File
        File file = new File(multipartFile.getOriginalFilename());
        multipartFile.transferTo(file);

        HashMap<String, Object> resusltMap = ThumbnailService.exportThumbnail(file);
        return resusltMap;
    }*/
}