package com.finalproject.breeding.board.dto;

import com.finalproject.breeding.board.model.category.ProvinceAreas;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProvinceResponseDto {
    private Long provinceId;
    private String provinceName;

    public ProvinceResponseDto(ProvinceAreas provinceAreas){
        this.provinceId = provinceAreas.getId();
        this.provinceName = provinceAreas.getName();
    }
}
