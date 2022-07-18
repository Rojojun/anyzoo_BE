package com.finalproject.breeding.etc.repository;

import com.finalproject.breeding.etc.model.Heart;
import com.finalproject.breeding.user.User;
import com.finalproject.breeding.board.model.BoardMain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HeartRepository extends JpaRepository<Heart, Long> {
    Heart findByUserAndBoardMain(User user, BoardMain boardMain);

    Heart getHeartByUserAndBoardMain(User user, BoardMain boardMain);

    List<Heart> findAllByBoardMain(BoardMain boardMain);

    List<HeartMapping> findByUser(User user);
}
