package com.finalproject.breeding.repository;

import com.finalproject.breeding.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<CommentMapping>findAllByBoardMain_Id(Long boardMainId);


}
