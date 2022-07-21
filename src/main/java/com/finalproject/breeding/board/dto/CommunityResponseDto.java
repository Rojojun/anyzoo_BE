package com.finalproject.breeding.board.dto;

import com.finalproject.breeding.board.model.Community;
import com.finalproject.breeding.board.model.Post;
import com.finalproject.breeding.image.model.CommunityImage;
import com.finalproject.breeding.image.model.PostImage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CommunityResponseDto {
    private Long communityId;
    private Long boardMainId;
    private String boardKind;
//    private String category;
//    private String title;
    private String contents;
    private Long likeCnt;
    private LocalDateTime dateTime;
    private String nickname;
    private String userProfileImg;
    private List<CommunityImage> img;

    public CommunityResponseDto(Community community) {
        this.communityId = community.getId();
        this.boardMainId = community.getBoardMain().getId();
        this.boardKind = community.getBoardMain().getBoardKind().name();
        this.img = community.getCommunityImages();
        this.contents = community.getBoardMain().getContent();
        this.likeCnt = community.getBoardMain().getLikeCnt();
        this.dateTime = community.getBoardMain().getCreatedAt();
        this.nickname = community.getUser().getNickname();
        this.userProfileImg = community.getUser().getUserImage().getUrl();
        //this.category = community.getCommunityCategory().name();
    }
}
