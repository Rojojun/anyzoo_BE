package com.finalproject.breeding.chat.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.web.JsonPath;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
@NoArgsConstructor
public class ChatRoomMessage {

    // 메시지 타입 : 입장, 퇴장, 채팅
    public enum MessageType {
        ENTER, QUIT, TALK
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @JoinColumn(name = "CHAT_ROOM_USER_ID")
    @ManyToOne
    private ChatRoomUser chatRoomUser;

    @JoinColumn(name = "USER_ID")
    @ManyToOne
    private ChatRoomUser UserId;

    @JoinColumn(name = "CHAT_ROOM_ID")
    @ManyToOne
    private ChatRoom chatRoomId;

    @Column
    private String message;

    @Column
    @NotEmpty
    private MessageType type;

    @Builder
    public ChatRoomMessage(ChatRoomUser chatRoomUser, String message, MessageType type) {
        this.chatRoomUser = chatRoomUser;
        this.UserId = getUserId();
        this.chatRoomId = getChatRoomId();
        this.message = message;
        this.type = type;
    }
}
