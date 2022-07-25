package com.finalproject.breeding.board.dto;

import com.finalproject.breeding.image.model.TogetherImage;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@NoArgsConstructor
public class TogetherRequestDto {

    private String title;
    private String content;
    //@DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dday;
    private int limitPeople;
    private Long provinceId;
    private List<TogetherImage> togetherImages;

}
