package com.finalproject.breeding.repository;

import com.finalproject.breeding.model.Comment;
import com.finalproject.breeding.model.board.Reels;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReelsRepository extends JpaRepository<Reels, Long> {
    List<Reels>findAllByBoardMain_Id(Long boardMainId);


}
