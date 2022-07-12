package com.finalproject.breeding.board.model;

import com.finalproject.breeding.etc.model.Timestamped;
import com.finalproject.breeding.user.User;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Notice extends Timestamped {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @JoinColumn(name = "USER_ID")
    @ManyToOne
    private User user;

    @Column
    @NotNull
    private String title;

    @Column
    @NotNull
    private String img;

    @Column
    @NotNull
    private String content;
}
