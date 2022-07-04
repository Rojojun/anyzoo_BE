package com.finalproject.breeding.repository;

import com.finalproject.breeding.model.board.Post;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Calendar;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p " + "from Post p " + "where p.boardMain.createdAt > : time " + "order by p.boardMain.likeCnt desc ")
    List<Post> findByOrderByLikeCntDesc(PageRequest pageRequest, Calendar time);


}
