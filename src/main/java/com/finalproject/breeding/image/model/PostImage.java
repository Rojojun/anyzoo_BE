package com.finalproject.breeding.image.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.finalproject.breeding.board.model.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class PostImage {


    @Id
    @Column(name = "POSTIMAGE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true, name = "POST_ID")
    @JsonIgnore
    private Long postId;

    @Column(nullable = false)
    private String url;
    @Column(nullable = false)
    @JsonIgnore
    private String key;


    public PostImage(String key, String path){
        this.url = path;
        this.key = key;
    }
    public void updateToPost(Post post){
        this.postId = post.getId();
    }
}
