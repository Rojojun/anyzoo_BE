package com.finalproject.breeding.model;

import com.finalproject.breeding.model.board.BoardMain;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Heart {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @JoinColumn(name = "USER_ID")
    @ManyToOne
    private User user;

    @JoinColumn(name = "BOARDMAIN_ID")
    @ManyToOne
    private BoardMain boardMain;

    public Heart(User user, BoardMain boardMain){
        this.user = user;
        this.boardMain = boardMain;
    }

}
