package com.finalproject.breeding.dto;

import com.finalproject.breeding.model.PostNReelsCategory;
import com.finalproject.breeding.model.board.BoardMain;
import com.finalproject.breeding.model.board.Post;
import com.finalproject.breeding.model.category.BoardKind;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class PostRequestDto {

    private String content;
    private LocalDateTime dateTime;
    private String categoryName; //카테고리이름



}
