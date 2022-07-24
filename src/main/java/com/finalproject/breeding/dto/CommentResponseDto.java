package com.finalproject.breeding.dto;

import com.finalproject.breeding.etc.dto.response.CommentMapping;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CommentResponseDto {
private List<CommentMapping> comments;
private boolean last;

    public CommentResponseDto(List<CommentMapping> comments, boolean last) {
        this.comments = comments;
        this.last = last;

    }
}
