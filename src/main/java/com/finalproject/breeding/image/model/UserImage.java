package com.finalproject.breeding.image.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.finalproject.breeding.board.model.Post;
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
        this.url = "https://anyzoo-photo-bucket.s3.ap-northeast-2.amazonaws.com/user/53c5b426-5032-41af-86b7-49e654ffa3e6KakaoTalk_20220708_151849969.jpg";
    }
    public void updateToUser(User user){
        this.userId = user.getId();
    }
}
