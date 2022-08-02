package com.finalproject.breeding.chat.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChatRoomRequestDto {
    private String nickname;
    private Long togetherId;
}
