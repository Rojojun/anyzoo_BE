package com.finalproject.breeding.chat.dto;

import com.finalproject.breeding.chat.model.ChatRoomMessage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageRequestDto {

    private ChatRoomMessage.MessageType type;
    private Long roomId;
    private Long memberId;
    private String sender;
    private String message;
    private String createdAt;
}
