package com.finalproject.breeding.board.controller;

import com.finalproject.breeding.board.dto.NoticeMapping;
import com.finalproject.breeding.board.dto.NoticeRequestDto;
import com.finalproject.breeding.board.dto.NoticeResponseDto;
import com.finalproject.breeding.board.service.NoticeService;
import com.finalproject.breeding.etc.dto.MyDto;
import com.finalproject.breeding.board.repository.NoticeRepository;
import com.finalproject.breeding.user.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;
    private final NoticeRepository noticeRepository;


    //공지글 등록
    @PostMapping("/admin/notice")
    public ResponseEntity<MyDto> createNotice(@RequestBody NoticeRequestDto requestDto){
        String username = SecurityUtil.getCurrentUsername();
        return noticeService.createNotice(requestDto, username);
    }

    //공지글 삭제
    @DeleteMapping("/admin/notice/edit/{noticeId}")
    public ResponseEntity<MyDto> deleteNotice(@PathVariable Long noticeId){
        return noticeService.deleteNotice(noticeId);
    }

    //공지글 수정
    @PatchMapping ("/admin/notice/edit/{noticeId}")
    public ResponseEntity<MyDto> patchNotice(@RequestBody NoticeRequestDto requestDto,
                                              @PathVariable Long noticeId) {
        return noticeService.patchNotice(requestDto, noticeId);
    }

    //공지글 모두 불러오기
    @GetMapping("/admin/notice/getAll")
    public List<NoticeMapping> getAllNotice(){
        return noticeRepository.findAllByTitleNotNull();
    }

    //특정 공지글 불러오기
    @GetMapping("/admin/notice/{noticeId}")
    public NoticeResponseDto getNotice(@PathVariable Long noticeId){
        return noticeService.getNotice(noticeId);
    }

}
