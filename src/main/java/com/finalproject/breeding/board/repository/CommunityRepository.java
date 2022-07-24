package com.finalproject.breeding.board.repository;

import com.finalproject.breeding.board.dto.CommunityResponseDto;
import com.finalproject.breeding.board.model.Community;
import com.finalproject.breeding.board.model.category.CommunityCategory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityRepository extends JpaRepository<Community, Long> {

    

    Slice<CommunityResponseDto> findByOrderByBoardMainCreatedAtDesc(PageRequest pageRequest);

    CommunityResponseDto findByBoardMainId(Long boardMainId);

    Community findCommunityByBoardMainId(Long boardMainId);

    Slice<CommunityResponseDto> findByUserNicknameOrderByBoardMainCreatedAtDesc(PageRequest pageRequest, String nickname);
}
