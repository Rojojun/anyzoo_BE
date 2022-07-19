package com.finalproject.breeding.board.repository;

import com.finalproject.breeding.board.model.BoardMain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardMainRepository extends JpaRepository<BoardMain, Long> {
//    Slice<BoardMain> findByBoardKindAndUser(PageRequest pageRequest, BoardKind boardKind, User user);


}
