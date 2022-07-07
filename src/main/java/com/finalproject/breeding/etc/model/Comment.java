package com.finalproject.breeding.etc.model;

import com.finalproject.breeding.board.model.BoardMain;
import com.finalproject.breeding.user.User;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Comment extends Timestamped{
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
    private String content;
}
