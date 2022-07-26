package com.finalproject.breeding.board.controller;

import com.finalproject.breeding.board.dto.NoticeRequestDto;
import com.finalproject.breeding.board.dto.NoticeResponseDto;
import com.finalproject.breeding.board.repository.NoticeRepository;
import com.finalproject.breeding.board.service.NoticeService;
import com.finalproject.breeding.etc.dto.CommentMapping;
import com.finalproject.breeding.etc.dto.MyDto;
import com.finalproject.breeding.board.dto.NoticeMapping;
import com.finalproject.breeding.user.UserRole;
import com.finalproject.breeding.user.repository.UserRepository;
import com.finalproject.breeding.user.security.SecurityUtil;
import com.finalproject.breeding.user.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;
    private final NoticeRepository noticeRepository;

    private final UserRepository userRepository;


    //공지글 작성
    @PostMapping("/api/admin/notice")
    public ResponseEntity<MyDto> createNotice(@RequestBody NoticeRequestDto requestDto){
        String username = SecurityUtil.getCurrentUsername();
        UserRole role = userRepository.findByUsername(username).get().getUserRole();


        return noticeService.createNotice(requestDto, username, role);
    }

    //공지글 삭제
    @DeleteMapping("/api/admin/notice/edit/{noticeId}")
    public ResponseEntity<MyDto> deleteComment(@PathVariable Long noticeId){
        String username = SecurityUtil.getCurrentUsername();
        UserRole role = userRepository.findByUsername(username).get().getUserRole();

        return noticeService.deleteComment(noticeId,role);
    }

    //공지글 수정
    @PatchMapping("/api/admin/notice/edit/{noticeId}")
    public ResponseEntity<MyDto> patchNotice(@RequestBody NoticeRequestDto requestDto,
                                              @PathVariable Long noticeId) {
        String username = SecurityUtil.getCurrentUsername();
        UserRole role = userRepository.findByUsername(username).get().getUserRole();

        return noticeService.patchNotice(requestDto, noticeId, role);
    }

    //특정 공지글 불러오기
    @GetMapping("/api/notice/{noticeId}")
    public NoticeResponseDto getNotice(@PathVariable Long noticeId){
        return noticeService.getNotice(noticeId);
    }

    //공지글 모두 불러오기
    @GetMapping("/api/notice/getAll")
    public List<NoticeMapping> getAllNotice(){
        List<NoticeMapping> noticeMapping = noticeRepository.findAllByTitleNotNull();
        return noticeMapping;
    }


}
