package com.finalproject.breeding.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class User extends Timestamped{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(unique = true)
    @NotNull
    private String email;

    @Column
    @NotNull
    private String password;

    @Column
    @NotNull
    private String nickname;

    @Column
    private String img;

    @Column
    @NotNull
    private Long exp;

//    @Column(nullable = false)
//    private UserRole userRole;

    @Column
    private int tier;

    public User(String test) {
        this.email = test;
        this.password = test;
        this.nickname = test;
        this.exp = 0L;
        this.tier = 0;
    }
}
