package com.finalproject.breeding.etc.dto;

import com.finalproject.breeding.board.model.Post;
import com.finalproject.breeding.image.model.PostImage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MyPageResponseDto {
    private Long postId;
    private Long boardMainId;
    private String boardKind;
    private String postCategory;
    private String contents;
    private Long likeCnt;
    private LocalDateTime dateTime;
    private String nickname;
    private String userProfileImg;
    private List<PostImage> img;

    public MyPageResponseDto(Post post){

    }

}

