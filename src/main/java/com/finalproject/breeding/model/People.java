package com.finalproject.breeding.model;

import com.finalproject.breeding.model.board.WithPost;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class People extends Timestamped{

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
