package com.finalproject.breeding.dto;

import com.finalproject.breeding.user.UserRole;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoUserInfoDto {
    private Long kakaoId;
    private String nickname;
    private String email;
}
