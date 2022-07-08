package com.finalproject.breeding.board.model;


import com.finalproject.breeding.board.dto.ReelsRequestDto;
import com.finalproject.breeding.board.model.BoardMain;
import com.finalproject.breeding.board.model.category.PostNReelsCategory;
import com.finalproject.breeding.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @JoinColumn(name = "USER_ID")
    @ManyToOne
    private User user;

    @JoinColumn
    @Enumerated(EnumType.STRING)
    private PostNReelsCategory postNReelsCategory;

    @Column
    private String video;

    @Column
    private String titleImg;

    public Reels(ReelsRequestDto reelsRequestDto, BoardMain boardMain, User user) {
        this.boardMain = boardMain;
        this.user = user;
        this.video = reelsRequestDto.getVideo();
        this.titleImg = reelsRequestDto.getTitleImg();

        switch (reelsRequestDto.getCategoryName()) {
            case "comic":
                this.postNReelsCategory = PostNReelsCategory.COMIC;
                break;
            case "cool":
                this.postNReelsCategory = PostNReelsCategory.COOL;
                break;
            case "pretty":
                this.postNReelsCategory = PostNReelsCategory.PRETTY;
                break;
            case "cute":
                this.postNReelsCategory = PostNReelsCategory.CUTE;
                break;
        }
    }
}
