package com.finalproject.breeding.image.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AwsS3 {
    private String key;
    private String path;

    public AwsS3() {

    }

    @Builder
    public AwsS3(PostImage postImage) {
        this.key = postImage.getKey();
        this.path = postImage.getUrl();
    }

}
