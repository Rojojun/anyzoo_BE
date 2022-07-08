package com.finalproject.breeding.repository;

import com.finalproject.breeding.model.board.Reels;
import com.finalproject.breeding.model.category.PostNReelsCategory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReelsRepository extends JpaRepository<Reels, Long> {
    /*Slice<ReelsMapping> findByOrderByIdDesc(PageRequest pageRequest);

    Slice<ReelsMapping> findByPostNReelsCategory(PageRequest pageRequest, PostNReelsCategory postNReelsCategory);*/
}
