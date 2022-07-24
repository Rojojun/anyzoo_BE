package com.finalproject.breeding.etc.dto.response;

import com.finalproject.breeding.etc.model.Follow;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FollowerDto {
    private String nickname;
    private String userProfileImg;

    public FollowerDto(Follow follow) {
        this.nickname = follow.getFollower().getNickname();
        this.userProfileImg = follow.getFollower().getUserImage().getUrl();
    }
}
