package com.finalproject.breeding.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.finalproject.breeding.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment>findAllByBoardMainId(Long boardMainId);

}
