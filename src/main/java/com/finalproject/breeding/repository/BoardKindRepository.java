package com.finalproject.breeding.repository;

import com.finalproject.breeding.model.category.BoardKind;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardKindRepository extends JpaRepository<BoardKind, Long> {
    BoardKind findByBoardName(String boardName);


}
