package com.finalproject.breeding.board.dto;

import com.finalproject.breeding.board.model.Community;
import lombok.Getter;

@Getter
public class CommunityResDto {
    private final Long id;
    private final String title;
    private final String content;

    public CommunityResDto(Community community){
        this.id = community.getId();
        this.title = community.getTitle();
        this.content = community.getBoardMain().getContent();
    }
}
