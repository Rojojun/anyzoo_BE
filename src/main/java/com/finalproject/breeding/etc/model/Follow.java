package com.finalproject.breeding.etc.model;

import com.finalproject.breeding.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Follow {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;


    @JoinColumn(name = "FOLLOWER")
    @ManyToOne
    private User follower;

    @JoinColumn(name = "FOLLOWING")
    @ManyToOne
    private User following;

    public Follow(User follower, User following){
        this.follower = follower;
        this.following = following;
    }

}
