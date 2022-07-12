package com.finalproject.breeding.etc.repository;

import com.finalproject.breeding.etc.model.Comment;
import com.finalproject.breeding.repository.CommentMapping;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Slice<CommentMapping> findByBoardMainId(PageRequest pageRequest, Long boardMainId);



}
