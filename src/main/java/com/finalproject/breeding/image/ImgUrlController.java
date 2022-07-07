package com.finalproject.breeding.image;

import com.finalproject.breeding.etc.dto.StatusResponseDto;
import com.finalproject.breeding.image.model.CommunityImage;
import com.finalproject.breeding.image.model.PostImage;
import com.finalproject.breeding.image.model.UserImage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ImgUrlController {

    private final AwsS3Service awsS3Service;

    @PostMapping("/api/post/image")
    public List<PostImage> uploadPost(@RequestPart(value = "file", required = false)List<MultipartFile> multipartFiles)throws IOException{
        return awsS3Service.uploadPost(multipartFiles,"post");
    }
    @PostMapping("/api/community/image")
    public List<CommunityImage> uploadPosts(@RequestPart(value = "file", required = false)List<MultipartFile> multipartFiles)throws IOException{
        return awsS3Service.uploadCommunity(multipartFiles, "community");
    }

    @PostMapping("/api/user/image")
    public UserImage uploadUser(@RequestPart(value = "file", required = false)MultipartFile multipartFile)throws IOException{
        return awsS3Service.uploadUser(multipartFile,"user");
    }

    @DeleteMapping("/api/image/{id}")
    public ResponseEntity<Object> remove(@PathVariable Long id){
        awsS3Service.remove(id);
        return new ResponseEntity<>(new StatusResponseDto("삭제 되었습니다.", ""), HttpStatus.OK);
    }

}
