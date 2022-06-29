package com.finalproject.breeding.repository;

import com.finalproject.breeding.model.board.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
