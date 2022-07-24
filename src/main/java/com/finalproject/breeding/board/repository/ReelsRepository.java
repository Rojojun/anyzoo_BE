package com.finalproject.breeding.board.repository;

import com.finalproject.breeding.board.dto.PostResponseDto;
import com.finalproject.breeding.board.dto.ReelsResponseDto;
import com.finalproject.breeding.board.model.Reels;
import com.finalproject.breeding.board.model.category.PostNReelsCategory;
import com.finalproject.breeding.etc.dto.response.MyPageReelsDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReelsRepository extends JpaRepository<Reels, Long> {
    /*Slice<ReelsMapping> findByOrderByIdDesc(PageRequest pageRequest);

    Slice<ReelsMapping> findByPostNReelsCategory(PageRequest pageRequest, PostNReelsCategory postNReelsCategory);*/

    Slice<ReelsResponseDto> findReelsByPostNReelsCategory(PageRequest pageRequest, PostNReelsCategory postNReelsCategory);
    Slice<ReelsResponseDto> findReelsByPostNReelsCategoryOrderByBoardMainLikeCntDesc(PageRequest pageRequest, PostNReelsCategory postNReelsCategory);
    Slice<ReelsResponseDto> findReelsByOrderByBoardMainLikeCntDesc(PageRequest pageRequest);
    Slice<ReelsResponseDto> findReelsByOrderByIdDesc(PageRequest pageRequest);
    Reels findByBoardMainId(Long boardMainId);

    Slice<MyPageReelsDto> findByUserNicknameOrderByBoardMainCreatedAtDesc(PageRequest pageRequest, String nickname);
/*    Long findVideoByreelsId(Long reelsId);
    Long findThumbnailByreelsId(Long reelsId);*/
}
