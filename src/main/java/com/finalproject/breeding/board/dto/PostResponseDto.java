package com.finalproject.breeding.board.dto;

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
public class PostResponseDto {
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
    private Long commentCnt;

    public PostResponseDto(Post post) {
        this.postId = post.getId();
        this.boardKind = post.getBoardMain().getBoardKind().name();
        this.nickname = post.getUser().getNickname();
        this.userProfileImg = post.getUser().getUserImage().getUrl();
        this.img = post.getPostImage();
        this.boardMainId = post.getBoardMain().getId();
        this.contents = post.getBoardMain().getContent();
        this.likeCnt = post.getBoardMain().getLikeCnt();
        this.dateTime = post.getBoardMain().getCreatedAt();
        this.postCategory = post.getPostNReelsCategory().name();
        this.commentCnt = post.getBoardMain().getCommentCnt();
    }


}
