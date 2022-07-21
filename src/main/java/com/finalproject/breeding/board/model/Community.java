package com.finalproject.breeding.board.model;

import com.finalproject.breeding.board.dto.CommunityRequestDto;
import com.finalproject.breeding.board.model.category.CommunityCategory;
import com.finalproject.breeding.image.model.CommunityImage;
import com.finalproject.breeding.user.User;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Community {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @JoinColumn(name = "USER_ID")
    @ManyToOne
    private User user;

//    @Column
//    private String title;
//
//    @JoinColumn
//    @Enumerated(EnumType.STRING)
//    private CommunityCategory communityCategory;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMMUNITY_ID")
    private List<CommunityImage> communityImages = new ArrayList<>();

    @JoinColumn(name = "BOARDMAIN_ID")
    @OneToOne
    private BoardMain boardMain;



    @Builder
    public Community(BoardMain boardMain, User user, List<CommunityImage> communityImages){
        this.boardMain = boardMain;
        this.user = user;
        this.communityImages = communityImages;
    }
    
}
