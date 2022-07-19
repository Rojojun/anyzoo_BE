package com.finalproject.breeding.board.dto;

import com.finalproject.breeding.etc.image.model.UserImage;

import java.time.LocalDateTime;

public interface NoticeMapping {
    Long getId();
    Long getUserId();
    String getTitle();
    String getUserNickname();
    UserImage getUserUserImage();
    LocalDateTime getCreatedAt();

}
