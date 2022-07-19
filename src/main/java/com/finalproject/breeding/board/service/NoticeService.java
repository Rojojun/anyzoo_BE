package com.finalproject.breeding.board.service;

import com.finalproject.breeding.board.dto.NoticeRequestDto;
import com.finalproject.breeding.board.dto.NoticeResponseDto;
import com.finalproject.breeding.board.model.Notice;
import com.finalproject.breeding.board.repository.NoticeRepository;
import com.finalproject.breeding.error.ErrorCode;
import com.finalproject.breeding.etc.dto.MyDto;
import com.finalproject.breeding.board.dto.NoticeMapping;
import com.finalproject.breeding.user.User;
import com.finalproject.breeding.user.repository.UserRepository;
import com.finalproject.breeding.user.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final UserRepository userRepository;


    //공지글 작성
    @Transactional
    public ResponseEntity<MyDto> createNotice(NoticeRequestDto requestDto, String username) {

        MyDto dto = new MyDto();
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        Optional<User> user1 = userRepository.findByUsername(username);
        dto.setStatus(ErrorCode.OK);

         if(!user1.isPresent()) {
            dto.setData(null);
            dto.setMessage("권한이 없습니다!");
        }else{
            User user = user1.get();
            Notice notice = new Notice(requestDto, user);
            Long NoticeId = noticeRepository.save(notice).getId();

            dto.setData(NoticeId);
            dto.setMessage("공지글 등록!");
        }
        return new ResponseEntity<>(dto, header, HttpStatus.OK);
    }


    //공지글 삭제
    @Transactional
    public ResponseEntity<MyDto> deleteComment(Long noticeId) {

        MyDto dto = new MyDto();
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        String userId = SecurityUtil.getCurrentUsername();
        Optional<Notice> notice =noticeRepository.findById(noticeId);
        dto.setStatus(ErrorCode.OK);
        dto.setData(noticeId);

        if(!notice.isPresent()){
            dto.setMessage("공지글이 없습니다!");
        }else if (!Objects.equals(userId, notice.get().getUser().getUsername())) {
            dto.setMessage("권한이 없습니다!");
        }else{
            noticeRepository.deleteById(noticeId);
            dto.setMessage("공지글 삭제!");
        }
        return new ResponseEntity<>(dto, header, HttpStatus.OK);

    }

    //공지글 수정
    @Transactional
    public ResponseEntity<MyDto> patchNotice(NoticeRequestDto requestDto, Long noticeId) {

        MyDto dto = new MyDto();
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        String userId = SecurityUtil.getCurrentUsername();
        Optional<Notice> notice =noticeRepository.findById(noticeId);
        dto.setStatus(ErrorCode.OK);
        dto.setData(noticeId);

        if(!notice.isPresent()){
            dto.setMessage("공지글이 없습니다!");
        }else if (!Objects.equals(userId, notice.get().getUser().getUsername())) {
            dto.setMessage("권한이 없습니다!");
        }else{
            Notice notice1 = notice.get();
            notice1.updateNotice(requestDto);
            dto.setMessage("공지글 수정!");
        }
        return new ResponseEntity<>(dto, header, HttpStatus.OK);

    }

    //특정 공지글 불러오기
    @Transactional
    public NoticeResponseDto getNotice(Long noticeId) {
        Notice notice1 = noticeRepository.findById(noticeId).get();
        NoticeResponseDto notice = new NoticeResponseDto(notice1);
        return notice;

    }

}
