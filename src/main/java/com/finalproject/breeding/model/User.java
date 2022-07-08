package com.finalproject.breeding.model;

import com.finalproject.breeding.dto.UserEditDto;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;

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
    @Size(min = 3, max = 20, message = "2 ~ 8 사이로 입력해주세요.")
    private String nickname;

    @Column
    private String img;

    //이메일 인증 여부
    @Column
    private boolean emailVerification;

    @Column
    private String userUUID;

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
    public User(String username, String password, String nickname, String img, UserRole userRole, Boolean emailVerification, String userUUID){
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.img = img;
        this.exp = 0L;
        this.userRole = userRole;
        this.tier = 0;
        this.emailVerification = emailVerification;
        this.userUUID = userUUID;
    }

    public void edit(UserEditDto userEditDto){
        this.nickname = userEditDto.getNickname();
        this.password = userEditDto.getPassword();
        this.img = userEditDto.getImg();
    }

    //이메일 인증 여부 메소드
    public void emailVerificationSuccess(){
        this.emailVerification = true;
    }

    //--------exp--------
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
