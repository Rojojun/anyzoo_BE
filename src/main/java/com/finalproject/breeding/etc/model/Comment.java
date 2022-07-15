package com.finalproject.breeding.etc.model;


import com.finalproject.breeding.board.model.BoardMain;
import com.finalproject.breeding.etc.dto.CommentRequestDto;
import com.finalproject.breeding.user.User;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Comment extends Timestamped {
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

    public Comment(CommentRequestDto requestDto, BoardMain boardMain, User user){

        this.comment = requestDto.getComment();
        this.boardMain = boardMain;
        this.user = user;
    }

    public void updateComment(CommentRequestDto requestDto){
        this.comment = requestDto.getComment();
    }


}