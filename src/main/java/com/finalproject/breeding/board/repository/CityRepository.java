package com.finalproject.breeding.board.repository;

import com.finalproject.breeding.board.dto.CityResponseDto;
import com.finalproject.breeding.board.model.category.CityAreas;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<CityAreas, Long> {

    List<CityResponseDto> findAllByOrderByIdAsc();
}
