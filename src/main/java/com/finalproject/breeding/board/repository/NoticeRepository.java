package com.finalproject.breeding.board.repository;

import com.finalproject.breeding.board.model.Notice;
import com.finalproject.breeding.board.dto.NoticeMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<NoticeMapping> findAllByTitleNotNull();
}
