package com.finalproject.breeding.etc.dto;

import java.time.LocalDateTime;

public interface ReplyMapping {
    Long getId();

    Long getUserId();

    String getReply();

    String getUserNickname();



    LocalDateTime getCreatedAt();


}
