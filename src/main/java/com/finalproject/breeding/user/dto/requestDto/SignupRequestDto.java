package com.finalproject.breeding.user.dto.requestDto;

import com.finalproject.breeding.model.User;
import com.finalproject.breeding.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
<<<<<<< HEAD:src/main/java/com/finalproject/breeding/user/dto/requestDto/SignupRequestDto.java
=======
import org.springframework.security.crypto.password.PasswordEncoder;
>>>>>>> parent of 2b69184... Merge branch 'jihun-dev' into main:src/main/java/com/finalproject/breeding/dto/SignupRequestDto.java

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
