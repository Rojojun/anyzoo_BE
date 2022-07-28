package com.finalproject.breeding.board.repository;

import com.finalproject.breeding.board.dto.TogetherResponseDto;
import com.finalproject.breeding.board.model.Together;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TogetherRepository extends JpaRepository<Together, Long> {
    Together findByBoardMainId(Long boardMainId);

    @Query("select t " + "from Together t " + "where t.provinceAreas.id = :provinceId " + "order by t.boardMain.createdAt desc " )
    Slice<TogetherResponseDto> findByProvinceIdOrderByBoardMainCreatedAtDesc(PageRequest pageRequest, Long provinceId);

    Slice<TogetherResponseDto> findByOrderByBoardMainCreatedAtDesc(PageRequest pageRequest);

    TogetherResponseDto findTogetherByBoardMainId(Long boardMainId);

    Slice<TogetherResponseDto> findByUserNicknameOrderByBoardMainCreatedAtDesc(PageRequest pageRequest, String nickname);
}
