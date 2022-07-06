package com.finalproject.breeding.repository;

import com.finalproject.breeding.model.board.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepositoryTest extends JpaRepository<Post, Long> {


/*    @Query("select p " + "from Post p " + "where p.boardMain.createdAt > : time " + "order by p.boardMain.likeCnt desc ")
    List<Post> findByOrderByLikeCntDesc(PageRequest pageRequest, Calendar time);*/


}
