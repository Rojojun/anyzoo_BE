package com.finalproject.breeding.board.repository;

import com.finalproject.breeding.board.model.Together;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TogetherRepository extends JpaRepository<Together, Long> {
    Together findByBoardMainId(Long boardMainId);
}
