package com.finalproject.breeding.chat.dto;

import com.finalproject.breeding.chat.model.ChatRoom;
import com.finalproject.breeding.user.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class ChatRoomResponseDto {
    private Long chatRoomId;
    private User user;

    public ChatRoomResponseDto(ChatRoom chatRoom, User writer) {
        this.chatRoomId = chatRoom.getId();
        this.user = writer;
    }
}
