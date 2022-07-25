package com.finalproject.breeding.image.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.finalproject.breeding.board.model.Post;
import com.finalproject.breeding.dto.KakaoUserInfoDto;
import com.finalproject.breeding.dto.SocialLoginRequestDto;
import com.finalproject.breeding.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@Entity
public class UserImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USER_ID")
    @JsonIgnore
    private Long userId;

    @Column(nullable = false)
    private String url;

    @JsonIgnore
    private String key;


    public UserImage(String key, String path) {
        this.url = path;
        this.key = key;
    }
    public UserImage(){
        this.url = "https://anyzoo-photo-bucket.s3.ap-northeast-2.amazonaws.com/user/45deb63e-1432-4a41-8637-74314093895a%EB%8B%A4%EC%9A%B4%EB%A1%9C%EB%93%9C.png";
    }

    public UserImage(User kakaoUser, KakaoUserInfoDto kakaoUserInfoDto) {
        this.url = kakaoUserInfoDto.getProfile_image();
        this.userId = kakaoUser.getId();
        this.key = "Kakao";
    }

    public UserImage(User tempUser, SocialLoginRequestDto socialLoginRequestDto) {
        this.url = socialLoginRequestDto.getPicture();
        this.userId = tempUser.getId();
        this.key = "Google";
    }

    public void updateToUser(User user){
        this.userId = user.getId();
    }
}
