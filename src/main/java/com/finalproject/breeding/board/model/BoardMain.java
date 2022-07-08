package com.finalproject.breeding.board.model;

import com.finalproject.breeding.board.dto.CommunityRequestDto;
import com.finalproject.breeding.board.dto.PostRequestDto;
import com.finalproject.breeding.board.dto.ReelsRequestDto;
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
    private String content;


    public void update(CommunityRequestDto communityRequestDto) {
        this.content = communityRequestDto.getContent();
    }

    public void plusLikeCnt(BoardMain boardMain){
        this.likeCnt = boardMain.getLikeCnt()+1L;
    }
    public void minusLikeCnt(BoardMain boardMain){
        this.likeCnt = boardMain.getLikeCnt()-1L;
    }

    public BoardMain(PostRequestDto postRequestDto) {
        this.content = postRequestDto.getContent();
        this.likeCnt = 0L;
        this.boardKind = BoardKind.POST;
    }

    public BoardMain(CommunityRequestDto communityRequestDto) {
        this.content = communityRequestDto.getContent();
        this.likeCnt = 0L;
        this.boardKind = BoardKind.COMMUNITY;
    }

    public BoardMain(ReelsRequestDto reelsRequestDto) {
        this.content = reelsRequestDto.getContent();
        this.likeCnt = 0L;
        this.boardKind = BoardKind.REELS;
    }
}