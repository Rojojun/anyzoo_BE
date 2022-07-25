package com.finalproject.breeding.image.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.finalproject.breeding.board.model.Together;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class TogetherImage {

    @Id
    @Column(name = "TOGETHERIMAGE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TOGETHER_ID")
    @JsonIgnore
    private Long togetherId;

    @Column(nullable = false)
    private String url;
    @Column(nullable = false)
    @JsonIgnore
    private String key;


    public TogetherImage(String key, String path){
        this.url = path;
        this.key = key;
    }
    public void updateToTogether(Together together){
        this.togetherId = together.getId();
    }
}
