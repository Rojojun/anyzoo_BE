package com.finalproject.breeding.dto;

import com.finalproject.breeding.model.User;
import com.finalproject.breeding.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
public class SignupRequestDto {
    private String username;
    private String nickname;
    private String password;
    private String img;

    public UsernamePasswordAuthenticationToken toAuthentication(){
        return new UsernamePasswordAuthenticationToken(username, password);
    }
}
