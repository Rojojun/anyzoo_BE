package com.finalproject.breeding.image.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class UserImage {


    @Id
    @Column(name = "USERIMAGE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true, name = "USER_ID")
    @JsonIgnore
    private Long userId;

    @Column(nullable = false)
    private String url;
    @Column(nullable = false)
    @JsonIgnore
    private String key;


    public UserImage(String key, String path) {
        this.url = path;
        this.key = key;
    }
}
