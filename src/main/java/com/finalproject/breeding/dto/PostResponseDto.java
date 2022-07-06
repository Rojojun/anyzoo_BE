package com.finalproject.breeding.dto;

import com.finalproject.breeding.model.board.BoardMain;
import com.finalproject.breeding.model.board.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostResponseDto {
    private Long id;
    private String boardKind;
    private String nickname;
    private String userProfileImg;
    private List<String> img;
    private Long boardMainId;
    private String contents;
    private Long likeCnt;
    private Long postCategoryId;
    private String postCategory;
    private LocalDateTime dateTime;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        //   this.boardKindId = post.getBoardMain().getBoardKind().getId();
        //   this.boardKind = post.getBoardMain().getBoardKind().getBoardName();
        this.nickname = post.getBoardMain().getUser().getNickname();
        this.userProfileImg = post.getBoardMain().getUser().getImg();
        //this.img = post.getBoardMain();
        this.boardMainId = post.getBoardMain().getId();
        this.contents = post.getBoardMain().getContent();
        this.likeCnt = post.getBoardMain().getLikeCnt();
        this.postCategoryId = post.getPostCategory().getId();
        this.postCategory = post.getPostCategory().getName();
        this.dateTime = post.getBoardMain().getCreatedAt();
    }
}
