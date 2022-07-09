package com.finalproject.breeding.model.board;

import com.finalproject.breeding.dto.CommunityRequestDto;
import com.finalproject.breeding.dto.PostRequestDto;
import com.finalproject.breeding.model.Timestamped;
import com.finalproject.breeding.model.User;
import com.finalproject.breeding.model.category.BoardKind;
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


    public void update(CommunityRequestDto communityRequestDto) {
        this.content = communityRequestDto.getContent();
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
    public void minusCommentCnt(BoardMain boardMain){
        this.commentCnt = boardMain.getCommentCnt()-1L;
    }

    public BoardMain(PostRequestDto postRequestDto) {
        this.content = postRequestDto.getContent();
        this.likeCnt = 0L;
        this.commentCnt = 0L;
        this.boardKind = BoardKind.POST;
    }
}
