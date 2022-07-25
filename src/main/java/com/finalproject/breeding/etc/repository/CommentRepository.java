package com.finalproject.breeding.etc.repository;

import com.finalproject.breeding.etc.model.Comment;
import com.finalproject.breeding.etc.dto.response.CommentMapping;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Slice<CommentMapping> findByBoardMainId(PageRequest pageRequest, Long boardMainId);

    @Query(value = "select max(comment.id) " +
            "from Comment comment " )
    Comment findMaxCommentId();



}
