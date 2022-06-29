package com.finalproject.breeding.model.board;

import com.finalproject.breeding.model.Timestamped;
import com.finalproject.breeding.model.User;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
