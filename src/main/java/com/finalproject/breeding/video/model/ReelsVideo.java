package com.finalproject.breeding.video.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.finalproject.breeding.board.model.Reels;
import lombok.Getter;
import javax.persistence.*;

@Getter
@Entity
public class ReelsVideo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "REELS_ID")
    @JsonIgnore
    private Long reelsId;

    @Column
    private String url;

    @JsonIgnore
    private String key;

    public ReelsVideo(String url, String key) {
        this.key = key;
        this.url = url;
    }

    public void updateToReels(Reels reeels) {
        this.reelsId = reeels.getId();
    }
}
