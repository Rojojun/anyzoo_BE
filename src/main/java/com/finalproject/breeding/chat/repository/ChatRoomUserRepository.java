package com.finalproject.breeding.chat.repository;

import com.finalproject.breeding.chat.model.ChatRoom;
import com.finalproject.breeding.chat.model.ChatRoomUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomUserRepository extends JpaRepository<ChatRoomUser, String> {
    List<ChatRoomUser> findChatRoomUserByUserId (ChatRoomUser chatRoomUser);
    List<ChatRoomUser> findChatRoomUsersByChatRoomId (ChatRoomUser chatRoom);
}
