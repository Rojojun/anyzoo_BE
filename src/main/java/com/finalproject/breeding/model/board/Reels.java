package com.finalproject.breeding.model.board;

import com.finalproject.breeding.dto.CommentRequestDto;
import com.finalproject.breeding.dto.ReelsRequestDto;
import com.finalproject.breeding.model.User;
import com.finalproject.breeding.model.category.PostCategory;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Reels {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @JoinColumn(name = "BOARDMAIN_ID")
    @OneToOne
    private BoardMain boardMain;

    @JoinColumn(name = "POSTCATEGORY_ID")
    @ManyToOne
    private PostCategory postCategory;

    @Column
    @NotNull
    private String video;

    @Column
    @NotNull
    private String titleImg;

    public Reels(ReelsRequestDto requestDto, BoardMain boardMain, PostCategory postCategory){
        this.video = requestDto.getVideo();
        this.titleImg = requestDto.getTitleImg();
        this.boardMain = boardMain;
        this.postCategory = postCategory;

    }
}
