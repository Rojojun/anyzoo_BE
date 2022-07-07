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


}
