package com.finalproject.breeding.chat.controller;

import com.finalproject.breeding.chat.dto.ChatMessageRequestDto;
import com.finalproject.breeding.chat.model.ChatRoomMessage;
import com.finalproject.breeding.chat.service.ChatService;
import com.finalproject.breeding.chat.service.RedisPublisher;
import com.finalproject.breeding.user.dto.requestDto.LoginDto;
import com.finalproject.breeding.user.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

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

        // 메시지 생성 시간 정보
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-DD");
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        String dateOutput = simpleDateFormat.format(date);
        messageRequestDto.setCreatedAt(dateOutput);

        // DTO로 채팅 메시지 객체 생성
        ChatRoomMessage chatRoomMessage = new ChatRoomMessage(messageRequestDto);

        // MySQL DB에 채팅 메시지 저장
        chatService.save(chatRoomMessage);

        // 웹소캣 통신으로 토픽 구독자들에게 메시지 전송
        chatService.sendChatMessage(chatRoomMessage);
    }
}
