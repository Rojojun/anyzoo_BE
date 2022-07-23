package com.finalproject.breeding.board.dto;

import com.finalproject.breeding.image.model.TogetherImage;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

@Getter
@NoArgsConstructor
public class TogetherRequestDto {

    private String title;
    private String content;
    private String categoryName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime date;
    private int limitPeople;
    private Long provinceId;
    private List<TogetherImage> togetherImages;

}
