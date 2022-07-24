package com.finalproject.breeding.etc.dto.response;

import com.finalproject.breeding.etc.model.Follow;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FollowingDto {
    private String nickname;
    private String userProfileImg;

    public FollowingDto(Follow follow){
        this.nickname = follow.getFollowing().getNickname();
        this.userProfileImg = follow.getFollowing().getUserImage().getUrl();
    }
}
