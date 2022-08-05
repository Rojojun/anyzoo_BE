package com.finalproject.breeding.chat.service;

import com.finalproject.breeding.chat.model.ChatRoom;
import com.finalproject.breeding.chat.model.ChatRoomMessage;
import com.finalproject.breeding.chat.repository.ChatRoomMessageRepository;
import com.finalproject.breeding.user.token.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.C;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class ChatService {
    // 채팅방에 발행되는 메시지를 처리할 Listener
    // 1:N 방식으로 topic처리 Listener
    private final ChannelTopic channelTopic;
    private final TokenProvider tokenProvider;
    private final ChatRoomMessageRepository chatRoomMessageRepository;
    private final RedisTemplate redisTemplate;
    // Web-Socket시 SecurityContextHolder 에서 저장
    // 1. WebSocket 인증 정보 저장
    public void saveAuthentication(Message<?> message) {
        // 아래의 객체를 사용하면 패킷에 접근 가능
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        String jwtToken = accessor.getFirstNativeHeader("token");
        boolean tokenValid = tokenProvider.validateToken(jwtToken);

        // 2. WebSocket에 올바른 토큰이 왔을 경우 SecurityContextHolder에 저장하는 작업 추가
        if(tokenValid) {
            Authentication authentication = tokenProvider.getAuthentication(jwtToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }
    // 메시지 전송
    public void sendChatMessage(ChatRoomMessage chatRoomMessage) {
        if (ChatRoomMessage.MessageType.ENTER.equals(chatRoomMessage.getType())) {
            log.info("Message Type is ENTER");
            chatRoomMessage.setMessage(chatRoomMessage.getSender() + "님이 입장하셨습니다.");
            chatRoomMessage.setMessage("[알림]");

            saveNotification(chatRoomMessage);
        }

        redisTemplate.convertAndSend(channelTopic.getTopic(), chatRoomMessage);
    }

    // 알림 저장
    private void saveNotification(ChatRoomMessage chatRoomMessage) {
        ChatRoomMessage message = new ChatRoomMessage();
        message.setType(chatRoomMessage.getType());
        message.setRoomId(chatRoomMessage.getRoomId());
        message.setSender(chatRoomMessage.getSender());
        message.setMessage(chatRoomMessage.getMessage());
        message.setCreatedAt(chatRoomMessage.getCreatedAt());
        chatRoomMessageRepository.save(message);
    }
    // 메시지 저장
    public void save(ChatRoomMessage chatRoomMessage) {
        ChatRoomMessage message = new ChatRoomMessage();
        message.setType(chatRoomMessage.getType());
        message.setMessage(chatRoomMessage.getMessage());
        message.setRoomId(chatRoomMessage.getRoomId());
        message.setSender(chatRoomMessage.getSender());
        message.setMessage(chatRoomMessage.getMessage());
        message.setCreatedAt(chatRoomMessage.getCreatedAt());

        chatRoomMessageRepository.save(message);
    }
    // 채팅방 전체 메시지 조회
    public List<ChatRoomMessage> getMessages(Long roomId) {
        return chatRoomMessageRepository.findAllMessage(roomId);
    }
}
