package com.finalproject.breeding.user.dto.requestDto;

import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Getter
public class SignupRequestDto {
    private String username;
    private String nickname;
    private String password;
    private String url;

    public UsernamePasswordAuthenticationToken toAuthentication(){
        return new UsernamePasswordAuthenticationToken(username, password);
    }
}
