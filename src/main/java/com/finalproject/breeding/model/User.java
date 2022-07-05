package com.finalproject.breeding.model;

import com.finalproject.breeding.dto.UserEditDto;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
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

    @Column
    @NotNull
    private int tier;

    @Builder
    public User(String username, String password, String nickname, String img, UserRole userRole){
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.img = img;
        this.exp = 0L;
        this.userRole = userRole;
        this.tier = 0;
    }

    public void edit(UserEditDto userEditDto){
        this.nickname = userEditDto.getNickname();
        this.password = userEditDto.getPassword();
        this.img = userEditDto.getImg();
    }

    public void oneLvUp(User user){
        this.tier = user.getTier()+1;
        this.exp = user.getExp()-10000L;
    }
    public void twoLvUp(User user){
        this.tier = user.getTier()+1;
        this.exp = user.getExp()-20000L;
    }
    public void threeLvUp(User user){
        this.tier = user.getTier()+1;
        this.exp = user.getExp()-40000L;
    }
    public void tenExpUp(User user){
        this.exp = user.getExp()+10L;
    }
    public void fiveExpUp(User user){
        this.exp = user.getExp()+5L;
    }
}
