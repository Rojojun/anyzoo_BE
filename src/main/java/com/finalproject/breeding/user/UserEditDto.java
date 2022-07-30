package com.finalproject.breeding.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserEditDto {
    String nickname;
    String phoneNumber;
    String newPassword;
    String oldPassword;
}
