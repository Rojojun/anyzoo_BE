package com.finalproject.breeding.chat.repository;

import com.finalproject.breeding.chat.model.ChatRoom;
import com.finalproject.breeding.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    List<ChatRoom> findAllByOrderByCreatedAtDesc();

    //List<ChatRoom> findAllByMemberListIsContaining(User user);

    List<ChatRoom> findAllByUserListIsContaining(User user);
}
