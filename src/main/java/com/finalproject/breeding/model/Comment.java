package com.finalproject.breeding.model;

import com.finalproject.breeding.dto.CommentRequestDto;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
public class Comment {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @JoinColumn(name = "USER_ID")
    @ManyToOne
    private User user;

    @JoinColumn(name = "BOARDMAIN_ID")
    @ManyToOne
    private BoardMain boardMain;

    @Column
    @NotNull
    private String comment;

    public Comment(CommentRequestDto requestDto, BoardMain boardMain){
        this.comment = requestDto.getComment();
        boardMain.getId();

    }
}
