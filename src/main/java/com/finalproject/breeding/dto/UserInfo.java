package com.finalproject.breeding.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserInfo {
    private String username;
    private String nickname;
    private String img;
    private int tier;
    private Long exp;
}
