package com.finalproject.breeding.etc.repository;

import com.finalproject.breeding.etc.dto.ReplyMapping;
import com.finalproject.breeding.etc.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findByCommentId(Long CommentId);
    List<ReplyMapping> findAllByCommentId(Long commentId);

}
