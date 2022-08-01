package com.finalproject.breeding.chat.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.breeding.chat.model.ChatRoomMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisSubscriber implements MessageListener {
    private final ObjectMapper objectMapper;
    private final SimpMessageSendingOperations messagingTemplate;
    private final RedisTemplate redisTemplate;
    /**
    *  Redis --메시지 발행 (Pub)--> onMessage --> 처리
    **/
    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            // redis에서 발행된 데이터를 받아서 deserialize
            String publishMessage = (String) redisTemplate.getStringSerializer().deserialize(message.getBody());
            // ChatRoomMessage 객체로 매핑
            ChatRoomMessage roomMessage = objectMapper.readValue(publishMessage, ChatRoomMessage.class);
            // WS 구독자에게 채팅 메시지 전달
            // SendTo의 역할을 대체하는거 같음 Ex) /sub/chat/room/1 (1번 룸 가정하에...)
            log.info("destination is (ROOM NUMBER): " + roomMessage.getChatRoomId());
            log.info("destination is (ROOM PATH WITH NUMBER) : /sub/chat/room/" + roomMessage.getChatRoomId());
            messagingTemplate.convertAndSend("/sub/chat/room/" + roomMessage.getChatRoomId(), roomMessage);
            } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
