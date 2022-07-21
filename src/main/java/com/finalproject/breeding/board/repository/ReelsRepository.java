package com.finalproject.breeding.board.repository;

import com.finalproject.breeding.board.dto.PostResponseDto;
import com.finalproject.breeding.board.dto.ReelsResponseDto;
import com.finalproject.breeding.board.model.Reels;
import com.finalproject.breeding.board.model.category.PostNReelsCategory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReelsRepository extends JpaRepository<Reels, Long> {
    /*Slice<ReelsMapping> findByOrderByIdDesc(PageRequest pageRequest);

    Slice<ReelsMapping> findByPostNReelsCategory(PageRequest pageRequest, PostNReelsCategory postNReelsCategory);*/

    Slice<ReelsResponseDto> findPostByPostNReelsCategory(PageRequest pageRequest, PostNReelsCategory postNReelsCategory);
    Slice<ReelsResponseDto> findPostByPostNReelsCategoryOrderByBoardMainLikeCntDesc(PageRequest pageRequest, PostNReelsCategory postNReelsCategory);
    Slice<ReelsResponseDto> findPostByOrderByBoardMainLikeCntDesc(PageRequest pageRequest);
    Slice<ReelsResponseDto> findPostByOrderByIdDesc(PageRequest pageRequest);
    Reels findByBoardMainId(Long boardMainId);
/*    Long findVideoByreelsId(Long reelsId);
    Long findThumbnailByreelsId(Long reelsId);*/
}
