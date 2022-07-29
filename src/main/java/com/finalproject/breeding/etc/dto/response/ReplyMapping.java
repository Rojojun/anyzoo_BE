package com.finalproject.breeding.etc.dto.response;

import com.finalproject.breeding.image.model.UserImage;

import java.time.LocalDateTime;

public interface ReplyMapping {

    Long getId();
    Long getUserId();
    String getReply();
    String getUserNickname();
    UserImage getUserUserImage();
    LocalDateTime getCreatedAt();

}
