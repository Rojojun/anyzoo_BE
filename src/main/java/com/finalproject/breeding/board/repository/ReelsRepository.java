package com.finalproject.breeding.board.repository;

import com.finalproject.breeding.board.model.Reels;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReelsRepository extends JpaRepository<Reels, Long> {
    /*Slice<ReelsMapping> findByOrderByIdDesc(PageRequest pageRequest);

    Slice<ReelsMapping> findByPostNReelsCategory(PageRequest pageRequest, PostNReelsCategory postNReelsCategory);*/
}
