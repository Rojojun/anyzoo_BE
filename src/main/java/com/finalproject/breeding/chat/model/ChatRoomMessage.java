package com.finalproject.breeding.chat.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.finalproject.breeding.chat.dto.ChatMessageRequestDto;
import lombok.*;
import org.springframework.data.web.JsonPath;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize
public class ChatRoomMessage {

    private static final long serialVersionUID = 6494678977089006639L;

    // 메시지 타입 : 입장, 퇴장, 채팅
    public enum MessageType {
        ENTER, QUIT, TALK
    }

    private MessageType type;
    private Long roomId;
    private Long memberId;
    private String sender;
    private String message;
    private String createdAt;
/*
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
    private MessageType type;*/

    @Builder
    public ChatRoomMessage(MessageType type, Long roomId, String sender, String senderEmail, String senderImg, String message, String createdAt) {
        this.type = type;
        this.roomId = roomId;
        this.sender = sender;
        this.message = message;
        this.createdAt = createdAt;
    }

    @Builder
    public ChatRoomMessage(ChatMessageRequestDto chatMessageRequestDto) {
        this.type = chatMessageRequestDto.getType();
        this.roomId = chatMessageRequestDto.getRoomId();
        this.memberId = chatMessageRequestDto.getMemberId();
        this.sender = chatMessageRequestDto.getSender();
        this.message = chatMessageRequestDto.getMessage();
        this.createdAt = chatMessageRequestDto.getCreatedAt();
    }
}
