package com.finalproject.breeding.model.board;


import com.finalproject.breeding.dto.PostRequest4EditDto;
import com.finalproject.breeding.dto.PostRequestDto;
import com.finalproject.breeding.model.category.PostNReelsCategory;
import com.finalproject.breeding.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Post {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @JoinColumn(name = "USER_ID")
    @ManyToOne
    private User user;

    @JoinColumn(name = "BOARDMAIN_ID")
    @OneToOne
    private BoardMain boardMain;

    @JoinColumn(nullable = false)
    @Enumerated(EnumType.STRING)
    private PostNReelsCategory postNReelsCategory;


    public Post(PostRequestDto postRequestDto, BoardMain boardMain, User user) {
        this.boardMain = boardMain;
        this.user = user;

        switch (postRequestDto.getCategoryName()) {
            case "comic":
                this.postNReelsCategory = PostNReelsCategory.COMIC;
                break;
            case "cool":
                this.postNReelsCategory = PostNReelsCategory.COOL;
                break;
            case "pretty":
                this.postNReelsCategory = PostNReelsCategory.PRETTY;
                break;
            case "cute":
                this.postNReelsCategory = PostNReelsCategory.CUTE;
                break;
        }
    }


    public void updatePost(PostRequest4EditDto requestDto) {

    }
}
