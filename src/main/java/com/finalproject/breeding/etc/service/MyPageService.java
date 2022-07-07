package com.finalproject.breeding.etc.service;

import com.finalproject.breeding.board.model.BoardMain;
import com.finalproject.breeding.board.model.category.BoardKind;
import com.finalproject.breeding.board.repository.BoardMainRepository;
import com.finalproject.breeding.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MyPageService {

//    private final BoardMainRepository boardMainRepository;
//
//    public Slice<BoardMain> getMyBoard(Long page, String boardname, User user) {
//        PageRequest pageRequest = PageRequest.of(Math.toIntExact(page), 4, Sort.by(Sort.Direction.DESC, "createdAt"));
//
//        return  boardMainRepository.findByBoardKindAndUser(pageRequest, boardKind, user);
//    }
}
