package com.finalproject.breeding.etc.dto;

import com.finalproject.breeding.board.model.Post;
import com.finalproject.breeding.image.model.PostImage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@NoArgsConstructor
public class MyPagePostResponseDto {
    private Long boardMainId;
    private Long likeCnt;
    private List<PostImage> img;

    public MyPagePostResponseDto(Post post){
        this.boardMainId = post.getBoardMain().getId();
        this.likeCnt = post.getBoardMain().getLikeCnt();
        this.img = post.getPostImage();
    }

}

