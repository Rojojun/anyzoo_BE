package com.finalproject.breeding.board.model;


import com.finalproject.breeding.board.dto.PostRequest4EditDto;
import com.finalproject.breeding.board.dto.PostRequestDto;
import com.finalproject.breeding.board.model.category.PostNReelsCategory;
import com.finalproject.breeding.image.model.PostImage;
import com.finalproject.breeding.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Post {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column
    private Long id;

    @JoinColumn(name = "USER_ID")
    @ManyToOne
    private User user;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private List<PostImage> postImage = new ArrayList<>();

    @JoinColumn(name = "BOARDMAIN_ID")
    @OneToOne
    private BoardMain boardMain;

    @JoinColumn(nullable = false)
    @Enumerated(EnumType.STRING)
    private PostNReelsCategory postNReelsCategory;




    public Post(PostRequestDto postRequestDto, BoardMain boardMain, User user, List<PostImage> postImages) {
        this.boardMain = boardMain;
        this.user = user;
        this.postImage = postImages;

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


    public void updatePost(PostRequest4EditDto requestDto, BoardMain boardMain, List<PostImage> postImage) {
        this.boardMain = boardMain;
        this.postImage = postImage;
        switch (requestDto.getCategoryName()) {
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
}
