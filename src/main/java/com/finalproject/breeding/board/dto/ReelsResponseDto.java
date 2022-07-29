package com.finalproject.breeding.board.dto;

import com.finalproject.breeding.board.model.Reels;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ReelsResponseDto {
    private Long reelsId;
    private Long boardMainId;
    private String boardKind;
    private String reelsCategory;
    private String contents;
    private Long likeCnt;
    private LocalDateTime dateTime;
    private String nickname;
    private String userProfileImg;
    private String video;
    private String thumbnail;
    private  Long commentCnt;

    public ReelsResponseDto(Reels reels) {
        this.reelsId = reels.getId();
        this.boardMainId = reels.getBoardMain().getId();
        this.boardKind = reels.getBoardMain().getBoardKind().name();
        this.reelsCategory = reels.getPostNReelsCategory().name();
        this.contents = reels.getBoardMain().getContent();
        this.likeCnt = reels.getBoardMain().getLikeCnt();
        this.nickname = reels.getUser().getNickname();
        this.dateTime = reels.getBoardMain().getCreatedAt();
        this.userProfileImg = reels.getUser().getUserImage().getUrl();
        this.video = reels.getVideo();
        this.thumbnail = reels.getTitleImg();
        this.commentCnt = reels.getBoardMain().getCommentCnt();
    }
}
