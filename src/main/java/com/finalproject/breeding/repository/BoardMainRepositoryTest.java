package com.finalproject.breeding.repository;

import com.finalproject.breeding.model.board.BoardMain;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardMainRepositoryTest extends JpaRepository<BoardMain, Long> {
//    Slice<BoardMain> findByBoardKind(PageRequest pageRequest, BoardKind boardKind);

}
