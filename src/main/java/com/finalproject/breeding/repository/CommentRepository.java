package com.finalproject.breeding.repository;

import com.finalproject.breeding.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment>findAllByBoardMain_Id(Long boardMainId);

}
