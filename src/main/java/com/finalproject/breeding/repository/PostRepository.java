package com.finalproject.breeding.repository;

import com.finalproject.breeding.model.board.Post;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.PostMapping;

public interface PostRepository extends JpaRepository <Post, Long> {
    //List<Post> findByOrderByLikeCntDesc();
    Slice<PostMapping> findByOrderByIdDesc(PageRequest pageRequest);

}
