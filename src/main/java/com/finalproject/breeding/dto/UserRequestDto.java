package com.finalproject.breeding.dto;

import com.finalproject.breeding.model.User;
import com.finalproject.breeding.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {
    private String username;
    private String nickname;
    private String password;
    private String img;

    public User toUser(PasswordEncoder passwordEncoder){
        return User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .userRole(UserRole.ROLE_USER)
                .nickname(nickname)
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication(){
        return new UsernamePasswordAuthenticationToken(username, password);
    }
}
