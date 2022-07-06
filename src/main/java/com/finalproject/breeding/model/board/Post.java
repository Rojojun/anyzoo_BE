package com.finalproject.breeding.model.board;

import com.finalproject.breeding.dto.PostRequest4EditDto;
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

    @JoinColumn(name = "BOARDMAIN_ID")
    @OneToOne
    private BoardMain boardMain;

    @JoinColumn(name = "POSTCATEGORY_ID")
    @ManyToOne
    private PostCategory postCategory;

    public Post(PostCategory postCategory, BoardMain boardMain) {
        this.boardMain = boardMain;
        this.postCategory = postCategory;
    }

    public void updatePost(PostRequest4EditDto requestDto) {

    }
}
