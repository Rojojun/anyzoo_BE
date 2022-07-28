package com.finalproject.breeding.image;


import com.finalproject.breeding.image.model.PostImage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ImageResponseDto {
    private String url;

    private String errorMessage;
    public ImageResponseDto(PostImage postImage){
        this.url = postImage.getUrl();
        this.errorMessage = "200 request : success";
    }

    public ImageResponseDto(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
