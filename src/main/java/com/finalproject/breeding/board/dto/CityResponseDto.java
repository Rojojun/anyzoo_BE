package com.finalproject.breeding.board.dto;

import com.finalproject.breeding.board.model.category.CityAreas;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CityResponseDto {
    private Long cityId;
    private String cityName;

    public CityResponseDto(CityAreas cityAreas){
        this.cityId = cityAreas.getId();
        this.cityName = cityAreas.getName();
    }
}
