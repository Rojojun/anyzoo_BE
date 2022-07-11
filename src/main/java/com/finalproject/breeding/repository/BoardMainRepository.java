package com.finalproject.breeding.repository;

import com.finalproject.breeding.model.board.BoardMain;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardMainRepository extends JpaRepository<BoardMain, Long> {
//    Slice<BoardMain> findByBoardKindAndUser(PageRequest pageRequest, BoardKind boardKind, User user);

}