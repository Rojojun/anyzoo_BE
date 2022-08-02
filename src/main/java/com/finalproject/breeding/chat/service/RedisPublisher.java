/*
package com.finalproject.breeding.chat.service;

import com.finalproject.breeding.chat.model.ChatRoomMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisPublisher {
    private final RedisTemplate<String, Object> redisTemplate;

    public void publish(ChannelTopic topic, ChatRoomMessage message) {
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }
}
*/
