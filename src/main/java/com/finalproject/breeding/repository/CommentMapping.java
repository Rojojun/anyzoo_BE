package com.finalproject.breeding.repository;

import java.time.LocalDateTime;

public interface CommentMapping {
    Long getId();

    Long getUserId();

    String getComment();

    String getUserNickname();

    String getUserImg();

    LocalDateTime getCreatedAt();




}
