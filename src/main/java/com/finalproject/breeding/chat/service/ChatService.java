package com.finalproject.breeding.chat.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class ChatService {
    // 채팅방에 발행되는 메시지를 처리할 Listener
    // 1:N 방식으로 topic처리 Listener
    private final RedisMessageListenerContainer redisMessageListener;
    // redis Subscrib System
}
