package com.finalproject.breeding.model;

import com.finalproject.breeding.model.board.BoardMain;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class CommentCount {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @JoinColumn(name = "BOARDMAIN_ID")
    @ManyToOne
    private BoardMain boardMain;

    public CommentCount(BoardMain boardMain){
        this.boardMain = boardMain;
    }
}
