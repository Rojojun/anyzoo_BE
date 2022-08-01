package com.finalproject.breeding.chat.controller;

import com.finalproject.breeding.chat.dto.ChatRoomListDto;
import com.finalproject.breeding.chat.dto.ChatRoomRequestDto;
import com.finalproject.breeding.chat.dto.ChatRoomResponseDto;
import com.finalproject.breeding.chat.model.ChatRoom;
import com.finalproject.breeding.chat.model.ChatRoomMessage;
import com.finalproject.breeding.chat.service.ChatRoomService;
import com.finalproject.breeding.chat.service.ChatService;
import com.finalproject.breeding.user.User;
import com.finalproject.breeding.user.security.SecurityUtil;
import com.finalproject.breeding.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
@CrossOrigin
public class ChatRoomController {
    private final ChatService chatService;
    private final ChatRoomService chatRoomService;
    private final UserService userService;

    // 채팅방 입장
    @PostMapping("/enter/{togetherId}")
    @ResponseBody
    public ChatRoomResponseDto chatRoomResponseDto(@RequestBody ChatRoomRequestDto requestDto, @PathVariable Long togetherId) {
        log.info("채팅방 생성 requestDto = {}", requestDto);
        String username = SecurityUtil.getCurrentUsername();
        log.info("현재 유저의 아이디 = {}", username);

        return chatRoomService.createChatRoom(requestDto, togetherId);
    }

    // 전체 채팅방 목록 조회
    @GetMapping("/channels")
    @ResponseBody
    public List<ChatRoomListDto> getAllChatRooms() {
        String username = SecurityUtil.getCurrentUsername();
        User user = userService.getUserObject(username);

        return chatRoomService.getAllChatRooms(user);
    }

    // 특정 채팅방 조회
    @GetMapping("/channels/{roomId}")
    @ResponseBody
    public ChatRoomResponseDto showChatRoom(@PathVariable Long roomId) {
        String username = SecurityUtil.getCurrentUsername();
        User user = userService.getUserObject(username);
        return chatRoomService.showChatRoom(roomId, user);
    }

    //특정 채팅방 나가기
    @DeleteMapping("/channel/{roomId}")
    @ResponseBody
    public boolean deleteChatRoom(@PathVariable Long roomId){
        return chatRoomService.leaveChatRoom(roomId);
    }

    //채팅방 내 메시지 전체 조회
    @GetMapping("/channel/{roomId}/messages")
    @ResponseBody
    public List<ChatRoomMessage> getRoomMessages(@PathVariable Long roomId) {
        return chatService.getMessages(roomId);
    }
}

