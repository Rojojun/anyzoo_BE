package com.finalproject.breeding.etc.model;

import com.finalproject.breeding.board.model.WithPost;
import com.finalproject.breeding.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    public People(User user, WithPost withPost){
        this.user = user;
        this.withPost = withPost;
    }

}
