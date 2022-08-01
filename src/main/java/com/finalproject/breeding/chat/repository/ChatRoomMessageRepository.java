package com.finalproject.breeding.chat.repository;

import com.finalproject.breeding.chat.model.ChatRoom;
import com.finalproject.breeding.chat.model.ChatRoomMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Repository
public class ChatRoomMessageRepository {
    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, Long, List<ChatRoomMessage>> opsHashOperationMessage;
    private static final String CHAT_MESSAGE = "CHAT_MESSAGE";

    @PostConstruct
    private void init() {
        opsHashOperationMessage = redisTemplate.opsForHash();
    }

    public ChatRoomMessage save(ChatRoomMessage chatRoomMessage) {
        log.info("chatMessage : {}", chatRoomMessage.getMessage());
        log.info("chatType : {}", chatRoomMessage.getType());
        redisTemplate.setHashKeySerializer(new Jackson2JsonRedisSerializer<>(Long.class));
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(ChatRoomMessage.class));

        // 에러 날 가능 성 존재
        Long roomId = chatRoomMessage.getRoomId();
        List<ChatRoomMessage> chatRoomMessages = opsHashOperationMessage.get(CHAT_MESSAGE, roomId);
        if (chatRoomMessages.equals(null)) {
            chatRoomMessages = new ArrayList<>();
        }
        chatRoomMessages.add(chatRoomMessage);

        opsHashOperationMessage.put(CHAT_MESSAGE, roomId, chatRoomMessages);

        return chatRoomMessage;
    }

    public List<ChatRoomMessage> findAllMessage(Long roomId) {
        // Deserialize = 예시) MultiPartFile --> File
        log.info("Data Passed");

        return opsHashOperationMessage.get(CHAT_MESSAGE, roomId);
    }

    public void delete(Long roomId) {
        opsHashOperationMessage.delete(CHAT_MESSAGE, roomId);
    }
}
