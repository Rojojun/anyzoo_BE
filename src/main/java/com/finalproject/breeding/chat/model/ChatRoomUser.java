package com.finalproject.breeding.chat.model;

import com.finalproject.breeding.etc.model.Timestamped;
import com.finalproject.breeding.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class ChatRoomUser extends Timestamped {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @JoinColumn(name = "CHAT_ROOM_ID")
    @ManyToOne
    private ChatRoom chatRoomId;

    @JoinColumn(name = "USER_ID")
    @ManyToOne
    private User userId;

    @JoinColumn
    @Enumerated(EnumType.STRING)
    private Type type;

    @Builder
    public ChatRoomUser(ChatRoom chatRoomId, User userId, Type type) {
        this.userId = userId;
        this.chatRoomId = chatRoomId;
        if (type.equals("host")) {
            this.type = Type.HOST;
        } else if (type.equals("guest")) {
            this.type = Type.GUEST;
        }
    }
}
