package com.finalproject.breeding.dto;

import com.finalproject.breeding.repository.CommentMapping;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Slice;

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
