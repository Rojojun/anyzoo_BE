package com.finalproject.breeding.service;

import com.finalproject.breeding.model.User;
import com.finalproject.breeding.model.board.BoardMain;
import com.finalproject.breeding.model.category.BoardKind;
import com.finalproject.breeding.repository.BoardKindRepository;
import com.finalproject.breeding.repository.BoardMainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MyPageService {

//    private final BoardMainRepository boardMainRepository;
//    private final BoardKindRepository boardKindRepository;
//    public Slice<BoardMain> getMyBoard(Long page, Long boardKindId, User user) {
//        BoardKind boardKind = boardKindRepository.findById(boardKindId).orElseThrow(()->new NullPointerException("해당 카테고리는 없는 카테고리입니다."));
//        PageRequest pageRequest = PageRequest.of(Math.toIntExact(page), 4, Sort.by(Sort.Direction.DESC, "createdAt"));
//        return  boardMainRepository.findByBoardKindAndUser(pageRequest, boardKind, user);
//    }
}
