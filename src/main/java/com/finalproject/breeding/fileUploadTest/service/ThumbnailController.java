package com.finalproject.breeding.fileUploadTest.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Logger;

@Controller
@RequiredArgsConstructor
public class ThumbnailController {

    private final ThumbnailService thumbnailService;
    private final ConvertVedioService convertVedioService;
    //private final Logger logger = (Logger) LoggerFactory.getLogger(ThumbnailController.class);

    // thumbnail 페이지 이동
    @RequestMapping(value="/", method= RequestMethod.GET)
    public String thumbnail() {
        //logger.info("Request url : /");

        return "/thumbnail";
    }

    @RequestMapping(value="/thumbnail/export", method=RequestMethod.POST)
    public @ResponseBody HashMap<String, Object> exportThumbnail(@RequestParam("file") MultipartFile multipartFile) throws Exception {
        //logger.info("Request url : /thumbnail/export");

        // MultipartFile -> File
        File file = new File(multipartFile.getOriginalFilename());
        multipartFile.transferTo(file);

        HashMap<String, Object> resultMap = thumbnailService.exportThumbnail(file);
        return resultMap;
    }

    @RequestMapping(value="/file/convert", method=RequestMethod.POST)
    public @ResponseBody void convertVideo(@RequestParam("video") MultipartFile multipartFile) throws Exception {
        //logger.info("Request url : /thumbnail/export");

        // MultipartFile -> File
        File file = new File(multipartFile.getOriginalFilename());
        multipartFile.transferTo(file);

        File noevoFile = convertVedioService.videoEncode(file);
    }
}
