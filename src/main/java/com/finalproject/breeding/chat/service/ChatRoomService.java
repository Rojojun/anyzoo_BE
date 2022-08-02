package com.finalproject.breeding.chat.service;

import com.finalproject.breeding.board.model.Together;
import com.finalproject.breeding.board.repository.TogetherRepository;
import com.finalproject.breeding.chat.dto.ChatRoomListDto;
import com.finalproject.breeding.chat.dto.ChatRoomRequestDto;
import com.finalproject.breeding.chat.dto.ChatRoomResponseDto;
import com.finalproject.breeding.chat.model.ChatRoom;
import com.finalproject.breeding.chat.model.ChatRoomMessage;
import com.finalproject.breeding.chat.repository.ChatRoomMessageRepository;
import com.finalproject.breeding.chat.repository.ChatRoomRepository;
import com.finalproject.breeding.user.User;
import com.finalproject.breeding.user.repository.UserRepository;
import com.finalproject.breeding.user.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.common.reflection.XMember;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    // REDIS
    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, Long> hashOperationsEnterInfo;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMessageRepository chatRoomMessageRepository;
    private final UserRepository userRepository;
    private final TogetherRepository togetherRepository;
    private final ChatService chatService;
    public static final String ENTER_INFO = "ENTER_INFO";  // 채팅룸에 입장한 USER의 sessionId와 채팅룸 id를 맵핑한 정보

    @PostConstruct
    private void init() {
        hashOperationsEnterInfo = redisTemplate.opsForHash();
    }

    // 유저가 입장한 채팅방 ID와 유저 세션 ID 매핑 정보 저장
    // 22번째 줄에서 이미 중요 정보 매핑
    public void setUserEnterInfo(String sessionId, Long roomId) {
        redisTemplate.setHashKeySerializer(new Jackson2JsonRedisSerializer<>(String.class));
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Long.class));

        hashOperationsEnterInfo.put(ENTER_INFO, sessionId, roomId);
    }

    // 유저 세션으로 입장해 있는 채팅방 ID 조회
    public Long getUserEnterRoomId(String sessionId) {
        return hashOperationsEnterInfo.get(ENTER_INFO, sessionId);
    }

    // 유저 세션 정보와 매핑된 채팅방 ID삭제
    // 실시간으로 유저가 보는 방(1) : 유저가 매핑 되어 있는 방 (1)
    public void removeUserEnterInfo(String sessionId) {
        hashOperationsEnterInfo.delete(ENTER_INFO, sessionId);
    }

    // destination 정보에서 roomId 추출 (String)
    public String getRoomId(String destination) {
        int lastIndex = destination.lastIndexOf('/');
        if (lastIndex != -1) {
            return destination.substring(lastIndex + 1);
        }
        else
            return "";
    }

    // 채팅방 생성
    public ChatRoomResponseDto createChatRoom(ChatRoomRequestDto requestDto, Long togetherId) {
        String username = SecurityUtil.getCurrentUsername();
        Together together = togetherRepository.findByBoardMainId(togetherId);
        User writer = userRepository.findByUsername(username).orElse(null);
        User applicant = userRepository.findByNickname(requestDto.getNickname()).orElseThrow(
                () -> new IllegalArgumentException("해당 하는 유저를 찾을 수가 없습니다."));

        boolean isExist = false;
        List<ChatRoom> writerChatRoomList = chatRoomRepository.findAllByUserListIsContaining(writer);
        for (ChatRoom chatRoom : writerChatRoomList) {
            isExist = chatRoom.getUserList().contains(applicant);
        }
        if (isExist) {
            return null;
        }
        assert writer != null;
        ChatRoom chatRoom = new ChatRoom(together, writer, applicant);
        chatRoomRepository.save(chatRoom);


        return new ChatRoomResponseDto(chatRoom, writer);
    }

    // 전체 채팅방 조회
    public List<ChatRoomListDto> getAllChatRooms(User user) {
        List<ChatRoomListDto> userChatRoom = new ArrayList<>();
        for (ChatRoom chatRoom : chatRoomRepository.findAllByOrderByCreatedAtDesc()) {
            if (chatRoom.getUserList().contains(user)) {
                userChatRoom.add(new ChatRoomListDto(chatRoom.getTogetherName().getBoardMain().getId(), chatRoom, chatRoom.getUserList().get(0)));
            }
        }
        return userChatRoom;
    }

    // 특정 채팅방 조회
    public ChatRoomResponseDto showChatRoom(Long roomId, User user) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(
                () -> new IllegalArgumentException("채팅방이 존재 하지 않습니다.")
        );
        return new ChatRoomResponseDto(chatRoom, user);
    }

    // 특정 채팅방 나가기
    // 나간 유저 : 나간 액션 처리
    // 방에 있는 유저 : [xx]님이 나가셨습니다.
    @Transactional
    public boolean leaveChatRoom(Long roomId) {
        String username = SecurityUtil.getCurrentUsername();
        User loginUser = userRepository.findByUsername(username).orElse(null);

        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElseThrow(
                () -> new NullPointerException("해당하는 채팅방이 없습니다.")
        );
        // 나간 유저를 채팅방 리스트에서 제거
        chatRoom.getUserList().remove(loginUser);

        // 현재 채팅룸에 남은 사람이 아무도 없다면 자동으로 채팅방을 폭파
        if (chatRoom.getUserList().size() == 0){
            // 채팅방 삭제 전 채팅 메시지 삭제
            chatRoomMessageRepository.delete(roomId);
            chatRoomRepository.deleteById(roomId);
        } else {
            // 한 명이라도 남아있는 경우 나가기 누른 사람의 퇴장 메시지를 호출
            assert loginUser != null;
            chatService.sendChatMessage(
                    ChatRoomMessage.builder()
                            .type(ChatRoomMessage.MessageType.QUIT)
                            .roomId(roomId)
                            .sender(loginUser.getNickname())
                            .build()
            );
        }
        return true;
    }
}