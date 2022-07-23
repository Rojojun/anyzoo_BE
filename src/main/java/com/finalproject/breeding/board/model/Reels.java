package com.finalproject.breeding.board.model;

import com.finalproject.breeding.board.dto.ReelsRequest4EditDto;
import com.finalproject.breeding.board.dto.ReelsRequestDto;
import com.finalproject.breeding.board.model.category.PostNReelsCategory;
import com.finalproject.breeding.user.User;
import com.finalproject.breeding.video.model.ReelsThumbnail;
import com.finalproject.breeding.video.model.ReelsVideo;
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

    @OneToOne
    @JoinColumn(name = "REELS_VIDEO_ID")
    private ReelsVideo video;

    @OneToOne
    @JoinColumn(name = "REELS_THUMBNAIL_ID")
    private ReelsThumbnail titleImg;

    public Reels(ReelsRequestDto reelsRequestDto, BoardMain boardMain, User user, ReelsVideo video, ReelsThumbnail titleImg) {
        this.boardMain = boardMain;
        this.user = user;
        this.video = video;
        this.titleImg = titleImg;

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

    public void updateReels(ReelsRequest4EditDto requestDto, BoardMain boardMain) {
        this.boardMain = boardMain;
        switch (requestDto.getCategoryName()) {
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
