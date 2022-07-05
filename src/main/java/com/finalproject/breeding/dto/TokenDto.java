package com.finalproject.breeding.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenDto {
    private String username;
    private String nickname;
    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpiresIn;
}
