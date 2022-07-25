package com.finalproject.breeding.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SocialLoginRequestDto {
    private String email;
    private String name;
    private String picture;
    private String userRole;
    private String access_token;
}
