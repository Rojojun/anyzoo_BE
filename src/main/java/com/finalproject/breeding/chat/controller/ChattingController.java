package com.finalproject.breeding.chat.controller;

import com.finalproject.breeding.chat.dto.ChatMessageRequestDto;
import com.finalproject.breeding.chat.service.ChatService;
import com.finalproject.breeding.chat.service.RedisPublisher;
import com.finalproject.breeding.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin
public class ChattingController {
    private final RedisPublisher redisPublisher;
    private final UserService userService;
    private final ChatService chatService;
    /**
     * /pub/chat/message/ 에서 들어오는 메시지 처리
     */
    @MessageMapping("/chat/message")
    public void message(ChatMessageRequestDto messageRequestDto) {

    }
}
