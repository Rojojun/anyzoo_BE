package com.finalproject.breeding.util;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class S3VideoUploader {

    private final AmazonS3Client amazonS3Client;
    private final VideoEncode videoEncode;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;  // S3 버킷 이름

    public String upload(MultipartFile multipartFile, String dirName, Boolean isVideo) throws Exception {
        File uploadFile = convert(multipartFile).orElseThrow(() -> new IllegalArgumentException("비디오 컨버트 실패"));

        if (isVideo) {
            File convertFile = new File(System.getProperty("user.dir") + "/" + multipartFile.getOriginalFilename());
            String randomVideoName = UUID.randomUUID().toString();

            if (convertFile.createNewFile()) {
                try (FileOutputStream fileOutputStream = new FileOutputStream(convertFile)) {
                    // File의 데이터를 사용하기 위한 OutputStream 선언
                    // FileOutputStream : 파일로 바이트 단위의 출력을 내보내는 클래스
                    fileOutputStream.write(multipartFile.getBytes());
                }
            }
            videoEncode.videoEncode(convertFile.getAbsolutePath(),System.getProperty("user.dir") + "/video" + multipartFile.getOriginalFilename());
            videoEncode.exportThumbnail(convertFile.getAbsolutePath(), System.getProperty("user.dir") + "/thumbnail" + multipartFile.getName() + ".png");
            File file = new File(System.getProperty("user.dir") + "/video" + multipartFile.getOriginalFilename());
            File thumbnailFile = new File(System.getProperty("user.dir") +  "/thumbnail" + multipartFile.getName() + ".png");

            removeNewFile(uploadFile);
            if (videoEncode.getVideoLength(file.getAbsolutePath()) < 15){
                return upload(file, thumbnailFile, dirName, true, randomVideoName);
            }
/*            File shortFile = new File(System.getProperty("user.dir") + "/video" + multipartFile.getOriginalFilename());
            upload(shortFile,dirName,true,randomVideoName);*/

            return upload(file, thumbnailFile, dirName,false,randomVideoName);

        }else {
            return upload(uploadFile, uploadFile, dirName,false,UUID.randomUUID().toString());
        }
    }
    // S3로 파일 업로드하기
    public String upload(File uploadVideoFile ,File uploadThumbnailFile, String dirName, Boolean isShort, String uuid) {
        if(isShort){
            String fileName = dirName + "/" + uuid +".short";   // S3에 저장된 파일 이름
            String thumbnail = dirName + "/" + uuid +".thumbnail";
            return putS3(uploadVideoFile, uploadThumbnailFile, fileName, thumbnail);
        }
    // String fileName = dirName + "/" + uuid +".mp4";
        String fileName = dirName + "/" +uuid +"." + uploadVideoFile.getName().substring(uploadVideoFile.getName().lastIndexOf(".")+1);   // S3에 저장된 파일 이름
        String thumbnail =  dirName + "/" +uuid +"." + uploadThumbnailFile.getName().substring(uploadThumbnailFile.getName().lastIndexOf(".")+1);
        return putS3(uploadVideoFile, uploadThumbnailFile, fileName, thumbnail);
    }
    // S3로 업로드
    private String putS3(File uploadFile, File uploadThumbnailFile, String fileName, String thumbnail) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        amazonS3Client.putObject(new PutObjectRequest(bucket, thumbnail, uploadThumbnailFile).withCannedAcl(CannedAccessControlList.PublicRead));
        removeNewFile(uploadFile);
        removeNewFile(uploadThumbnailFile);
        return amazonS3Client.getUrl(bucket, fileName).toString() + "\n" + amazonS3Client.getUrl(bucket, thumbnail).toString();
    }
    // S3로 삭제
    public void deleteS3(String fileName)
    {
        Boolean isExistObject = amazonS3Client.doesObjectExist(bucket,fileName);
        if (isExistObject)
        {
            amazonS3Client.deleteObject(bucket,fileName);
        }
        else{
            log.warn("삭제할 s3파일이 존재하지 않습니다.");
        }
    }
    public S3Object getObject(String fileName)
    {
        Boolean isExistObject = amazonS3Client.doesObjectExist(bucket, fileName);
        if (isExistObject)
        {
            return amazonS3Client.getObject(bucket, fileName);
        }
        else{
            throw new IllegalArgumentException("s3 버킷에 파일이 존재하지 않습니다.");
        }
    }
    // 로컬에 저장된 이미지 지우기
    public void removeNewFile(File targetFile) {
        targetFile.delete();
        log.info("{} delete success", targetFile.getName());
    }
    public Optional<File> convert(MultipartFile multipartFile) throws IOException {
        System.out.println(System.getProperty("user.dir") + "/");
        System.out.println(multipartFile.getOriginalFilename());
        File convertFile = new File(System.getProperty("user.dir") + "/" + multipartFile.getOriginalFilename());

        System.out.println(System.getProperty("user.dir") + "/" + multipartFile.getOriginalFilename());
        // 바로 위에서 지정한 경로에 conver된 video가 생성됨, 경로의 문제가 있으면 생성 불가능
        /*if (convertFile.createNewFile()) {
            try (FileOutputStream fileOutputStream = new FileOutputStream(convertFile)) {
                // File의 데이터를 사용하기 위한 OutputStream 선언
                // FileOutputStream : 파일로 바이트 단위의 출력을 내보내는 클래스
                fileOutputStream.write(multipartFile.getBytes());
            }
           */ return Optional.of(convertFile);
        }
/*
        return Optional.empty();
*/
    }

