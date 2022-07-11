package com.finalproject.breeding.user.dto.requestDto;

import com.finalproject.breeding.image.model.UserImage;
import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;


@Getter
public class SignupRequestDto {
    private String username;
    private String nickname;
    private String password;
    private Long userImage;

    public UsernamePasswordAuthenticationToken toAuthentication(){
        return new UsernamePasswordAuthenticationToken(username, password);
    }
}
