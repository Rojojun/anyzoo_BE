package com.finalproject.breeding.etc.repository;

import com.finalproject.breeding.etc.dto.response.ReplyMapping;
import com.finalproject.breeding.etc.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findByCommentId(Long commentId);
    List<ReplyMapping> findAllByCommentId(Long commentId);



}
