package com.finalproject.breeding.user;

import com.finalproject.breeding.etc.model.Timestamped;
import com.finalproject.breeding.image.model.UserImage;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class User extends Timestamped {
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

    @OneToOne
    @JoinColumn
    private UserImage userImage;
    @Column
    @NotNull
    private int tier;

    @Column
    @NotNull
    private Long exp;

    @Column
    private Long follower;

    @Column
    private Long following;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole userRole;


    @Builder
    public User(String username, String password, String nickname, UserImage userImage, UserRole userRole){
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.userImage = userImage;
        this.exp = 0L;
        this.userRole = userRole;
        this.tier = 0;
        this.follower = 0L;
        this.following = 0L;
    }

    public void edit(UserEditDto userEditDto){
        this.nickname = userEditDto.getNickname();
        this.password = userEditDto.getPassword();
        //this.userImage = userEditDto.getImg();
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

    public void following(User user){
        this.following = user.getFollowing()+1L;
    }
    public void follower(User user){
        this.follower = user.getFollower()+1L;
    }
    public void unFollowing(User user){
        this.following = user.getFollowing()-1L;
    }
    public void unFollower(User user){
        this.follower = user.getFollower()-1L;
    }
}
