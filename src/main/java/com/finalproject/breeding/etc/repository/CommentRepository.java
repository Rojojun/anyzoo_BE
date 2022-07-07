package com.finalproject.breeding.etc.repository;

import com.finalproject.breeding.etc.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
