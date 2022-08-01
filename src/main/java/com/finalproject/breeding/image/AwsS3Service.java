package com.finalproject.breeding.image;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.finalproject.breeding.error.CustomException;
import com.finalproject.breeding.error.ErrorCode;
import com.finalproject.breeding.image.model.*;
import com.finalproject.breeding.image.repository.CommunityImageRepository;
import com.finalproject.breeding.image.repository.PostImageRepository;
import com.finalproject.breeding.image.repository.TogetherImageRepository;
import com.finalproject.breeding.image.repository.UserImageRepository;
import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AwsS3Service {

    private final AmazonS3 amazonS3;
    private final PostImageRepository postImageRepository;
    private final UserImageRepository userImageRepository;
    private final CommunityImageRepository communityImageRepository;
    private final TogetherImageRepository togetherImageRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    /*ArrayList<String> accessableLists = new ArrayList<>();
    public ArrayList<String> setAccessableLists() {
        accessableLists.add(".jpg");
        accessableLists.add(".png");
        accessableLists.add(".gif");

        return accessableLists;
    }*/

    //    public PostImage uploadPost(MultipartFile multipartFile, String dirName) throws IOException, java.io.IOException {
//        File file = convertMultipartFileToFile(multipartFile)
//                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File convert fail"));
//
//        String key = randomFileName(file, dirName);
//        String path = putS3(file, key);
//        removeFile(file);
//
//        return postImageRepository.save(new PostImage(key,path));

//        return AwsS3
//                .builder()
//                .key(key)
//                .path(path)
//                .build();
//    }

    public List<PostImage> uploadPost(List<MultipartFile> multipartFiles, String dirName) throws IOException, java.io.IOException{

        // 파일 확장자명 벨리데이션 체크
        ArrayList<String> accessableLists = new ArrayList<>();
        accessableLists.add(".jpg");
        accessableLists.add(".jpeg");
        accessableLists.add(".png");
        accessableLists.add(".gif");

        List<PostImage> postImages = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            String ext = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
            if (!accessableLists.contains(ext)) {
                log.error("500 Error : 사진 파일이 아니거나, 지원하지 않는 확장 파일입니다.");
                throw new CustomException(ErrorCode.EXTRACTION_VALIDATION_ERROR);
            }
        }

       for (MultipartFile multipartFile : multipartFiles){
           File file = convertMultipartFileToFile(multipartFile).orElseThrow(()->new CustomException(ErrorCode.IMAGE_UPLOAD_ERROR));
           String key = randomFileName(file, dirName);
           String path = putS3(file, key);
           removeFile(file);
           postImages.add(postImageRepository.save(new PostImage(key,path)));
       }

        return postImages;
    }

    public List<CommunityImage> uploadCommunity(List<MultipartFile> multipartFiles, String dirName) throws IOException, java.io.IOException{

        List<CommunityImage> communityImages = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles){
            File file = convertMultipartFileToFile(multipartFile).orElseThrow(()->new IllegalArgumentException("MultipartFile -> File convert fail"));
            String key = randomFileName(file, dirName);
            String path = putS3(file, key);
            removeFile(file);
            communityImages.add(communityImageRepository.save(new CommunityImage(key,path)));
        }
        return communityImages;
    }

    public List<TogetherImage> uploadTogether(List<MultipartFile> multipartFiles, String dirName) throws IOException, java.io.IOException{

        List<TogetherImage> togetherImages = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles){
            File file = convertMultipartFileToFile(multipartFile).orElseThrow(()->new IllegalArgumentException("MultipartFile -> File convert fail"));
            String key = randomFileName(file, dirName);
            String path = putS3(file, key);
            removeFile(file);
            togetherImages.add(togetherImageRepository.save(new TogetherImage(key,path)));
        }
        return togetherImages;
    }

    public UserImage uploadUser(MultipartFile multipartFile, String dirName) throws IOException, java.io.IOException {
        ArrayList<String> accessableLists = new ArrayList<>();
        accessableLists.add(".jpg");
        accessableLists.add(".jpeg");
        accessableLists.add(".png");
        accessableLists.add(".gif");

        File file = convertMultipartFileToFile(multipartFile)
                .orElseThrow(() -> new CustomException(ErrorCode.IMAGE_UPLOAD_ERROR));

        String ext = file.getName().substring(file.getName().lastIndexOf("."));

        if(!accessableLists.contains(ext)){
            log.error("500 Error : 사진 파일이 아니거나, 지원하지 않는 확장 파일입니다.");
            throw new CustomException(ErrorCode.EXTRACTION_VALIDATION_ERROR);
        }
        String key = randomFileName(file, dirName);
        String path = putS3(file, key);
        removeFile(file);

        return userImageRepository.save(new UserImage(key, path));

    }

    private String randomFileName(File file, String dirName) {
        return dirName + "/" + UUID.randomUUID() + file.getName();
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3.putObject(new PutObjectRequest(bucket, fileName, uploadFile)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return getS3(bucket, fileName);
    }

    private String getS3(String bucket, String fileName) {
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    private void removeFile(File file) {
        file.delete();
    }

    public Optional<File> convertMultipartFileToFile(MultipartFile multipartFile) throws IOException, java.io.IOException {
        File file = new File(System.getProperty("user.dir") + "/" + multipartFile.getOriginalFilename());

        if (file.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(file)){
                fos.write(multipartFile.getBytes());
            }
            return Optional.of(file);
        }
        return Optional.empty();
    }

    public void remove(String key) {
        if (!amazonS3.doesObjectExist(bucket, key)) {
            throw new AmazonS3Exception("Object " +key+ " does not exist!");
        }
        amazonS3.deleteObject(bucket, key);
    }

    public void removePostImages(Long postId) {
        List<PostImage> postImages = postImageRepository.findPostImageByPostId(postId);
        for(PostImage postImage : postImages){
            remove(postImage.getKey());
            postImageRepository.delete(postImage);
        }
    }
    public void removeCommunityImages(Long communityId) {
        List<CommunityImage> communityImages = communityImageRepository.findByCommunityId(communityId);
        for(CommunityImage communityImage : communityImages){
            remove(communityImage.getKey());
            communityImageRepository.delete(communityImage);
        }
    }
    public void removeTogetherImages(Long togetherId) {
        List<TogetherImage> togetherImages = togetherImageRepository.findByTogetherId(togetherId);
        for(TogetherImage togetherImage : togetherImages){
            remove(togetherImage.getKey());
            togetherImageRepository.delete(togetherImage);
        }
    }
    public void removeUserImage(Long userId){
        UserImage userImage = userImageRepository.findByUserId(userId);
        remove(userImage.getKey());
        userImageRepository.delete(userImage);
    }
}
