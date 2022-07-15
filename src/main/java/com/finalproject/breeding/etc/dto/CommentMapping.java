package com.finalproject.breeding.etc.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.finalproject.breeding.image.model.UserImage;

import java.awt.print.Pageable;
import java.time.LocalDateTime;

public interface CommentMapping {

    Long getId();
    Long getUserId();
    String getComment();
    String getUserNickname();
    UserImage getUserUserImage();
    LocalDateTime getCreatedAt();










}
