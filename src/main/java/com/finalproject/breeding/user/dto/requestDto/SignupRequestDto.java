package com.finalproject.breeding.user.dto.requestDto;

<<<<<<< HEAD:src/main/java/com/finalproject/breeding/user/dto/requestDto/SignupRequestDto.java
import com.finalproject.breeding.image.model.UserImage;
import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

=======
import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
>>>>>>> jihun-dev:src/main/java/com/finalproject/breeding/dto/SignupRequestDto.java

@Getter
public class SignupRequestDto {

    @NotBlank
    private String username;
    @Size(min = 2, max = 20)
    private String nickname;
    @NotBlank
    private String password;
<<<<<<< HEAD:src/main/java/com/finalproject/breeding/user/dto/requestDto/SignupRequestDto.java
    private UserImage userImage;
=======
    @NotBlank
    private String phoneNumber;
    private String img;
>>>>>>> jihun-dev:src/main/java/com/finalproject/breeding/dto/SignupRequestDto.java

    public UsernamePasswordAuthenticationToken toAuthentication(){
        return new UsernamePasswordAuthenticationToken(username, password);
    }
}
