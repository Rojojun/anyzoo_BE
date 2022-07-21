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

    @Column
    private String title;

    @JoinColumn
    @Enumerated(EnumType.STRING)
    private CommunityCategory communityCategory;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMMUNITY_ID")
    private List<CommunityImage> communityImages = new ArrayList<>();

    @JoinColumn(name = "BOARDMAIN_ID")
    @OneToOne
    private BoardMain boardMain;


    public Community(CommunityRequestDto communityRequestDto, BoardMain boardMain, User user, List<CommunityImage> communityImages){
        this.boardMain = boardMain;
        this.user = user;
        //this.title = communityRequestDto.getTitle();
        this.communityImages = communityImages;

//        switch (communityRequestDto.getCategoryName()) {
//            case "qna":
//                this.communityCategory = CommunityCategory.QNA;
//                break;
//            case "free":
//                this.communityCategory = CommunityCategory.FREE;
//                break;
//            case "review":
//                this.communityCategory = CommunityCategory.REVIEW;
//                break;
//        }

    }



//    public void update(CommunityRequestDto communityRequestDto) {
//        this.title = communityRequestDto.getTitle();
//        this.communityImages = communityRequestDto.getCommunityImages();
//    }
//
//    public void updateTitle(CommunityRequestDto communityRequestDto) {
//        this.title = communityRequestDto.getTitle();
//    }
}
