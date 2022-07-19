package com.finalproject.breeding.etc.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import com.finalproject.breeding.image.model.UserImage;

public interface CommentMapping {

    Long getId();
    Long getUserId();
    String getComment();
    String getUserNickname();
    UserImage getUserUserImage();
    LocalDateTime getCreatedAt();










}
