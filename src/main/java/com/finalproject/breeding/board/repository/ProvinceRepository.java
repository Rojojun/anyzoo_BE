package com.finalproject.breeding.board.repository;

import com.finalproject.breeding.board.dto.ProvinceResponseDto;
import com.finalproject.breeding.board.model.category.ProvinceAreas;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProvinceRepository extends JpaRepository<ProvinceAreas, Long> {
    List<ProvinceResponseDto> findAllByCityAreasId(Long cityId);
}
