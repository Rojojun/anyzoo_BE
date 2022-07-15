package com.finalproject.breeding.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class EmailVerificationRequestDto {
    private String email;
    private String authToken;
    LocalDateTime currentTime;
}
