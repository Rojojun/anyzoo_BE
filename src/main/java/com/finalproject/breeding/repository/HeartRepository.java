package com.finalproject.breeding.repository;

import com.finalproject.breeding.model.Heart;
import com.finalproject.breeding.model.User;
import com.finalproject.breeding.model.board.BoardMain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface HeartRepository extends JpaRepository<Heart, Long> {
    Heart findByUserAndBoardMain(User user, BoardMain boardMain);

    Heart getHeartByUserAndBoardMain(User user, BoardMain boardMain);

    List<Heart> findAllByBoardMain(BoardMain boardMain);

    List<HeartMapping> findByUser(User user);
}
