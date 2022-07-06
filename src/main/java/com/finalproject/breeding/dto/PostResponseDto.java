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
    private String nickname;
    private String userImg;
    private List<String> img;
    private String contents;
    private String boardKind;
    private Long likeCnt;
    private String postCategory;
    private LocalDateTime dateTime;

    public PostResponseDto(Post post) {
    //    this.boardKind = post.getBoardMain().getBoardKind().getBoardName();
        this.id = post.getId();
        this.nickname = post.getBoardMain().getUser().getNickname();
        this.userImg = post.getBoardMain().getUser().getImg();
       // this.img = post.getBoardMain().
        this.contents = post.getBoardMain().getContent();
        this.likeCnt = post.getBoardMain().getLikeCnt();
        this.postCategory = post.getPostCategory().getName();
        this.dateTime = post.getBoardMain().getCreatedAt();
    }
}
