package com.finalproject.breeding.dto;

import com.finalproject.breeding.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    //private String username;
    private String nickname;
    private String message;
    private String accesstoken;

    private String refreshtoken;

    public UserResponseDto(String message) {
        this.message = message;
    }

    public UserResponseDto(SocialTokenDto socialTokenDto, String message) {
        this.accesstoken = socialTokenDto.getAccessToken();
        this.refreshtoken = socialTokenDto.getRefreshToken();
        this.nickname = socialTokenDto.getNickname();
        this.message = message;
    }

    public static UserResponseDto of(User user) {
        return new UserResponseDto(user.getUsername());
    }
}
