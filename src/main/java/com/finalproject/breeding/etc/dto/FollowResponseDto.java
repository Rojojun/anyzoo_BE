package com.finalproject.breeding.etc.dto;

import com.finalproject.breeding.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FollowResponseDto {
    private String nickname;
    private String userProfileImg;

    public FollowResponseDto(User user){
        this.nickname = user.getNickname();
        this.userProfileImg = user.getUserImage().getUrl();
    }
}
