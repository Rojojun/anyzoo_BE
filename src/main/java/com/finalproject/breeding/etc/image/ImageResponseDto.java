package com.finalproject.breeding.etc.image;


import com.finalproject.breeding.etc.image.model.PostImage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ImageResponseDto {
    private String url;

    public ImageResponseDto(PostImage postImage){
        this.url = postImage.getUrl();
    }
}
