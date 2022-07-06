package com.finalproject.breeding.controller;

import com.finalproject.breeding.service.BoardKindService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardKindConrtoller {
    private final BoardKindRepository boardKindRepository;
    private final BoardKindService boardKindService;
    // Admin User만 접근 할 수 있는 페이지로 변경 예정
    // SecurityConfig에서 수정
    @PostMapping("/api/admin/create/{boardKind}")
    public void createBoardKind(@PathVariable String boardKind) {
        String boardkind = boardKind;
        boardKindService.createBoardKind(boardkind);
    }
}
