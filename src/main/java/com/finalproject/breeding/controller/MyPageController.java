package com.finalproject.breeding.controller;

import com.finalproject.breeding.model.User;
import com.finalproject.breeding.model.board.BoardMain;
import com.finalproject.breeding.service.MyPageService;
import com.finalproject.breeding.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class MyPageController {

    private final MyPageService myPageService;
    private final UserService userService;

    @GetMapping("/api/mypage/{boardKindId}")
    public Slice<BoardMain> getMyBoard(HttpServletRequest httpServletRequest,
                                       @PathVariable Long boardKindId){
        User user = userService.getUser();

        Long page = Long.parseLong(httpServletRequest.getParameter("page"));
        return myPageService.getMyBoard(page, boardKindId, user);
    }
}
