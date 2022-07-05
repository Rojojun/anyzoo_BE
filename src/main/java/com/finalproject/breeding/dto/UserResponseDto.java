package com.finalproject.breeding.dto;

import com.finalproject.breeding.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto{
    //private String username;
    private String nickname;
    private String message;
    private String accesstoken;

    public UserResponseDto(String message) {
        this.message = message;
    }

    public UserResponseDto(TokenDto tokenDto, String message) {
        this.accesstoken = tokenDto.getAccessToken();
        this.nickname = tokenDto.getNickname();
        this.message = message;
    }

    public static UserResponseDto of(User user){
        return new UserResponseDto(user.getUsername());
    }
//    public UserResponseDto(String accesstoken, String message){
//        this.message = message;
//        this.accesstoken = accesstoken;
//    }
}
