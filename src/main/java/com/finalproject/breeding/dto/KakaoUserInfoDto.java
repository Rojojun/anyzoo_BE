package com.finalproject.breeding.dto;

import com.finalproject.breeding.user.UserRole;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class KakaoUserInfoDto {
    private Long kakaoId;
    private String profile_image;

    public KakaoUserInfoDto(Long kakaoId, String profile_image){
        this.kakaoId = kakaoId;
        this.profile_image = profile_image;
    }
}


