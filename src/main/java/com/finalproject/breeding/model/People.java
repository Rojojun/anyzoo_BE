package com.finalproject.breeding.model;

import com.finalproject.breeding.model.board.WithPost;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
public class People {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @JoinColumn(name = "WITHPOST_ID")
    @ManyToOne
    private WithPost withPost;

    @JoinColumn(name = "USER_ID")
    @ManyToOne
    private User user;

}
