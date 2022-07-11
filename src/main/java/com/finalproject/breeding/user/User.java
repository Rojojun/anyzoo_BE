package com.finalproject.breeding.user;

import com.finalproject.breeding.etc.model.Timestamped;
import com.finalproject.breeding.image.model.UserImage;
import com.finalproject.breeding.dto.NewPasswordDto;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @OneToOne
    @JoinColumn(name = "USERIMAGE_ID")
    private UserImage userImage;
    @Column
    @NotNull
    private int tier;

    //이메일 인증 여부
    @Column
    private boolean verification;

    @Column(nullable = false)
    private String phoneNumber;

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
    public User(String username, String password, String nickname, UserRole userRole, Boolean verification, String phoneNumber, UserImage userImage){
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.userImage = userImage;
        this.userRole = userRole;
        this.exp = 0L;
        this.tier = 0;
        this.follower = 0L;
        this.following = 0L;
        this.verification = verification;
        this.phoneNumber = phoneNumber;
    }

    public void edit(UserEditDto userEditDto){
        this.nickname = userEditDto.getNickname();
        this.password = userEditDto.getPassword();
        //this.userImage = userEditDto.getImg();
    }

    public void changePassword(String newPassword){
        this.password = newPassword;
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
