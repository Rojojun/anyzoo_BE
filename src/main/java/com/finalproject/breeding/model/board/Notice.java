package com.finalproject.breeding.model.board;

import com.finalproject.breeding.model.User;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
public class Notice {

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
