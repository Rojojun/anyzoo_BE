package com.finalproject.breeding.etc.repository;

import com.finalproject.breeding.etc.model.Comment;
import com.finalproject.breeding.etc.dto.CommentMapping;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Slice<CommentMapping> findByBoardMainId(PageRequest pageRequest, Long boardMainId);



}
