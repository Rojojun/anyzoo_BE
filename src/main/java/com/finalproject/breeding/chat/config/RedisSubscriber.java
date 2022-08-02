package com.finalproject.breeding.chat.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.breeding.chat.model.ChatRoomMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisSubscriber {
    private final ObjectMapper objectMapper;
    private final SimpMessageSendingOperations messagingTemplate;
    /**
    *  Redis --메시지 발행 (Pub)--> onMessage --> 처리
    **/
    public void sendMessage(/*Message message, byte[] pattern*/String publishMessage) {
        try {
            // redis에서 발행된 데이터를 받아서 deserialize
            // ChatRoomMessage 객체로 매핑
            ChatRoomMessage roomMessage = objectMapper.readValue(publishMessage, ChatRoomMessage.class);
            // WS 구독자에게 채팅 메시지 전달
            // SendTo의 역할을 대체하는거 같음 Ex) /sub/chat/room/1 (1번 룸 가정하에...)
            log.info("destination is (ROOM NUMBER): " + roomMessage.getRoomId());
            log.info("destination is (ROOM PATH WITH NUMBER) : /sub/chat/room/" + roomMessage.getRoomId());
            messagingTemplate.convertAndSend("/sub/chat/room/" + roomMessage.getRoomId(), roomMessage);
            } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
