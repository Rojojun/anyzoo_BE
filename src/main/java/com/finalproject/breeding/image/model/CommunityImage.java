package com.finalproject.breeding.image.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.finalproject.breeding.board.model.Community;
import com.finalproject.breeding.board.model.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class CommunityImage {

    @Id
    @Column(name = "COMMUNITYIMAGE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true, name = "COMMUNITY_ID")
    @JsonIgnore
    private Long communityId;

    @Column(nullable = false)
    private String url;
    @Column(nullable = false)
    @JsonIgnore
    private String key;


    public CommunityImage(String key, String path){
        this.url = path;
        this.key = key;
    }
    public void updateToCommunity(Community community){
        this.communityId = community.getId();
    }
}
