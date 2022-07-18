package com.finalproject.breeding.websocket;

import com.finalproject.breeding.user.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatController {
    private final RedisTemplate redisTemplate;
    private final ChannelTopic channelTopic;
    @MessageMapping("/chat/message")
    public void message(ChatMessage message){
        String nickname = SecurityUtil.getCurrentUsername();
        message.setSender(nickname); // 로그인유저 닉네임을 대화명으로 설정
        if (ChatMessage.MessageType.ENTER.equals(message.getType())){
            message.setSender("[알림]");
            message.setMessage(nickname+"님이 입장했습니다.");
        }
        redisTemplate.convertAndSend(channelTopic.getTopic(), message);
    }
}
