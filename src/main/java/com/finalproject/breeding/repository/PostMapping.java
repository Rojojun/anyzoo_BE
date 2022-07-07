package com.finalproject.breeding.repository;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

public interface PostMapping {

    String getId();
    default String getContent(){
        return getBoardMainContent();
    }
    default String getLikeCnt(){
        return getBoardMainLikeCnt();
    }
    default String getCategory(){return getPostNReelsCategory();}

    default String getBoardKind(){return getBoardMainBoardKind();}
    default LocalDateTime getDateTime(){return getBoardMainCreatedAt();}

    //String getBoardMainCreatedAt();

    //String getBoardMainBoardKindId();

    String getUserNickname();
    @JsonIgnore
    String getBoardMainBoardKind();

    @JsonIgnore
    LocalDateTime getBoardMainCreatedAt();


    @JsonIgnore
    String getPostNReelsCategory();




    @JsonIgnore
    String getBoardMainContent();

    @JsonIgnore
    String getBoardMainLikeCnt();
}
