package com.finalproject.breeding.video.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Entity
public class ReelsThumbnail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "REELS_VIDEO_ID")
    @JsonIgnore
    private Long reelsVideoId;

    @Column
    @NotBlank
    private String url;

    @JsonIgnore
    private String key;

    public ReelsThumbnail(String key, String url) {
        this.url = url;
        this.key = key;
    }

    public ReelsThumbnail() {
        this.url = "https://anyzoo-photo-bucket.s3.ap-northeast-2.amazonaws.com/user/45deb63e-1432-4a41-8637-74314093895a%EB%8B%A4%EC%9A%B4%EB%A1%9C%EB%93%9C.png";
    }
}
