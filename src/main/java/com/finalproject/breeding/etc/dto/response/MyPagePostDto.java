package com.finalproject.breeding.etc.dto.response;

import com.finalproject.breeding.board.model.Post;
import com.finalproject.breeding.image.model.PostImage;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MyPagePostDto {
    private Long boardMainId;
    private Long likeCnt;
    private List<PostImage> img;

    public MyPagePostDto(Post post){
        this.boardMainId = post.getBoardMain().getId();
        this.likeCnt = post.getBoardMain().getLikeCnt();
        this.img = post.getPostImage();
    }

}

