package com.finalproject.breeding.board.model;

import com.finalproject.breeding.board.dto.*;
import com.finalproject.breeding.etc.model.Timestamped;
import com.finalproject.breeding.board.model.category.BoardKind;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class BoardMain extends Timestamped {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BoardKind boardKind;

    @Column
    @NotNull
    private Long likeCnt;

    @Column
    @NotNull
    private Long commentCnt;

    @Column
    @NotNull
    private String content;


    public void updateCommunity(CommunityRequestDto communityRequestDto) {
        this.content = communityRequestDto.getContent();
    }
    public void updateTogether(TogetherRequestDto togetherRequestDto) {
        this.content = togetherRequestDto.getContent();
    }
    public void updatePost(PostRequest4EditDto postRequestDto) {
        this.content = postRequestDto.getContent();
    }

    public void plusLikeCnt(BoardMain boardMain){
        this.likeCnt = boardMain.getLikeCnt()+1L;
    }
    public void minusLikeCnt(BoardMain boardMain){
        this.likeCnt = boardMain.getLikeCnt()-1L;
    }

    public void plusCommentCnt(BoardMain boardMain){
        this.commentCnt = boardMain.getCommentCnt()+1L;
    }
    public void minusCommentCnt(BoardMain boardMain,Long replyCnt){
        this.commentCnt = boardMain.getCommentCnt()-1L-replyCnt;
    }
    public void minusReplyCnt(BoardMain boardMain){
        this.commentCnt = boardMain.getCommentCnt()-1L;
    }


    public BoardMain(PostRequestDto postRequestDto) {
        this.content = postRequestDto.getContent();
        this.likeCnt = 0L;
        this.commentCnt = 0L;
        this.boardKind = BoardKind.POST;
    }

    public BoardMain(CommunityRequestDto communityRequestDto) {
        this.content = communityRequestDto.getContent();
        this.likeCnt = 0L;
        this.commentCnt = 0L;
        this.boardKind = BoardKind.COMMUNITY;
    }

    public BoardMain(ReelsRequestDto reelsRequestDto) {
        this.content = reelsRequestDto.getContent();
        this.likeCnt = 0L;
        this.commentCnt = 0L;
        this.boardKind = BoardKind.REELS;
    }
    public BoardMain(TogetherRequestDto togetherRequestDto){
        this.content = togetherRequestDto.getContent();
        this.likeCnt = 0L;
        this.commentCnt = 0L;
        this.boardKind = BoardKind.Together;
    }

    public BoardMain(PostRequest4EditDto postRequest4EditDto) {
        this.content = postRequest4EditDto.getContent();
        this.boardKind = BoardKind.POST;
    }

    public void updateReels(ReelsRequest4EditDto reelsRequestDto) {
        this.content = reelsRequestDto.getContents();
    }
}
