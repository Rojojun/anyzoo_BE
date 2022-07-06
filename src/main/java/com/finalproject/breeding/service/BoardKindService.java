package com.finalproject.breeding.service;

import com.finalproject.breeding.model.category.BoardKind;
import com.finalproject.breeding.repository.BoardKindRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BoardKindService {
    private final BoardKindRepository boardKindRepository;
    public void createBoardKind(String boardkind) {
        BoardKind boardKind = new BoardKind(boardkind);
        boardKindRepository.save(boardKind);
    }
}
