package com.finalproject.breeding.board.service;

import com.finalproject.breeding.board.dto.NoticeRequestDto;
import com.finalproject.breeding.board.dto.NoticeResponseDto;
import com.finalproject.breeding.board.model.Notice;
import com.finalproject.breeding.error.ErrorCode;
import com.finalproject.breeding.etc.dto.MyDto;
import com.finalproject.breeding.board.repository.NoticeRepository;
import com.finalproject.breeding.user.User;
import com.finalproject.breeding.user.repository.UserRepository;
import com.finalproject.breeding.user.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final UserRepository userRepository;

    //공지글 등록
    @Transactional
    public ResponseEntity<MyDto> createNotice(NoticeRequestDto requestDto, String username) {

        MyDto dto = new MyDto();
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new NullPointerException("유저가 존재하지 않습니다."));

        Notice notice = new Notice(requestDto, user);
        Long NoticeId = noticeRepository.save(notice).getId();

        dto.setStatus(ErrorCode.OK);
        dto.setData(NoticeId);
        dto.setMessage("공지글 등록!");
        return new ResponseEntity<>(dto, header, HttpStatus.OK);
    }

    //공지글 삭제
    @Transactional
    public ResponseEntity<MyDto> deleteNotice(Long noticeId) {

        MyDto dto = new MyDto();
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        String userId = SecurityUtil.getCurrentUsername(); //현제 로그인한 유저 pk
        Optional<Notice> a =noticeRepository.findById(noticeId);
        String userId1 =a.get().getUser().getUsername();

        if (Objects.equals(userId, userId1)) {   //댓글의 닉네임와 일치한다면

            noticeRepository.deleteById(noticeId);

            dto.setStatus(ErrorCode.OK);
            dto.setData(noticeId);
            dto.setMessage("공지글 삭제!");
            return new ResponseEntity<>(dto, header, HttpStatus.OK);
        }else{
            dto.setStatus(ErrorCode.COMMENT_WRONG_INPUT);
            dto.setData(noticeId);
            dto.setMessage("권한이 없습니다!");
            return new ResponseEntity<>(dto,header, HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public ResponseEntity<MyDto> patchNotice(NoticeRequestDto requestDto, Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(
                ()-> new NullPointerException("공지글이 존재하지 않습니다."));

        MyDto dto = new MyDto();
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        String userId = SecurityUtil.getCurrentUsername(); //현제 로그인한 유저 pk
        Optional<Notice> a =noticeRepository.findById(noticeId);
        String userId1 =a.get().getUser().getUsername();

        if (Objects.equals(userId, userId1)) {   //댓글의 닉네임와 일치한다면
            notice.updateNotice(requestDto);
            dto.setStatus(ErrorCode.OK);
            dto.setData(noticeId);
            dto.setMessage("공지글 수정!");
            return new ResponseEntity<>(dto, header, HttpStatus.OK);
        }else{
            dto.setStatus(ErrorCode.COMMENT_WRONG_INPUT);
            dto.setData(noticeId);
            dto.setMessage("권한이 없습니다!");
            return new ResponseEntity<>(dto,header, HttpStatus.BAD_REQUEST);
        }

    }

//특정 공지글 불러오기
    @Transactional
    public NoticeResponseDto getNotice(Long noticeId) {

        Notice notice = noticeRepository.findById(noticeId).orElseThrow(
                ()-> new NullPointerException("공지글이 존재하지 않습니다."));
        NoticeResponseDto noticeResponseDto = new NoticeResponseDto(notice);

        return noticeResponseDto;
    }
}
