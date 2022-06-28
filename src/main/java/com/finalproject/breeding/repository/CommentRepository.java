package com.finalproject.breeding.repository;

import com.finalproject.breeding.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
