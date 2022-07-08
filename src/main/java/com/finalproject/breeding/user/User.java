package com.finalproject.breeding.user;

import com.finalproject.breeding.etc.model.Timestamped;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
<<<<<<< HEAD:src/main/java/com/finalproject/breeding/user/User.java
public class User extends Timestamped {
=======
public class User extends Timestamped{
>>>>>>> parent of 2b69184... Merge branch 'jihun-dev' into main:src/main/java/com/finalproject/breeding/model/User.java
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

    @Column(nullable = false)
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
