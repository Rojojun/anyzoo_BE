package com.finalproject.breeding.user.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserInfo {
    private String username;
    private String nickname;
    private int tier;
    private Long exp;
    private String img;
}
