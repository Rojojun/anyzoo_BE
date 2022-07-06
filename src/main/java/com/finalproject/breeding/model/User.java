package com.finalproject.breeding.model;

import com.finalproject.breeding.dto.UserEditDto;
import com.sun.istack.NotNull;
import lombok.Builder;
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
    private String username;

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

    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    private UserRole userRole;


    @Builder
    public User(String username, String password, String nickname, String img, Long exp, UserRole userRole){
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.img = img;
        this.exp = exp;
        this.userRole = userRole;
    }

    public void edit(UserEditDto userEditDto){
        this.nickname = userEditDto.getNickname();
        this.password = userEditDto.getPassword();
        this.img = userEditDto.getImg();
    }
}
