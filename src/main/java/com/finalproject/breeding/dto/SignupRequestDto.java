package com.finalproject.breeding.dto;

import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
public class SignupRequestDto {

    @NotBlank
    private String username;
    @Size(min = 2, max = 20)
    private String nickname;
    @NotBlank
    private String password;
    @NotBlank
    private String phoneNumber;
    private String img;

    public UsernamePasswordAuthenticationToken toAuthentication(){
        return new UsernamePasswordAuthenticationToken(username, password);
    }
}
