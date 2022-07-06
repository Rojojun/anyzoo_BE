package com.finalproject.breeding.repository;


import com.fasterxml.jackson.annotation.JsonIgnore;

public interface CommunityMapping {
    String getId();
    String getTitle();
    default String getContent(){
        return getBoardMainContent();
    }
    default String getLikeCnt(){
        return getBoardMainLikeCnt();
    }

    //String getBoardMainBoardKindId();

    String getUserNickname();




    @JsonIgnore
    String getBoardMainContent();

    @JsonIgnore
    String getBoardMainLikeCnt();


}
