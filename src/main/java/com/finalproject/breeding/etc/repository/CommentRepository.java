package com.finalproject.breeding.etc.repository;

<<<<<<< HEAD:src/main/java/com/finalproject/breeding/etc/repository/CommentRepository.java
import com.finalproject.breeding.etc.model.Comment;
=======
import com.finalproject.breeding.model.Comment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
>>>>>>> dohun-dev-1st-scope:src/main/java/com/finalproject/breeding/repository/CommentRepository.java
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Slice<CommentMapping> findByBoardMainId(PageRequest pageRequest, Long boardMainId);



}
