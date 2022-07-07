package com.finalproject.breeding.repository;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface PostMapping {

    String getId();
    default String getContent(){
        return getBoardMainContent();
    }
    default String getLikeCnt(){
        return getBoardMainLikeCnt();
    }
    //String getBoardMainCreatedAt();

    //String getBoardMainBoardKindId();

    String getUserNickname();




    @JsonIgnore
    String getBoardMainContent();

    @JsonIgnore
    String getBoardMainLikeCnt();
}
