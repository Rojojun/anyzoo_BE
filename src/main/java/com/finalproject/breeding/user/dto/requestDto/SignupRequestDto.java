package com.finalproject.breeding.user.dto.requestDto;

import com.finalproject.breeding.image.model.UserImage;
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
    private UserImage userImage;
    @NotBlank
    private String phoneNumber;

    public UsernamePasswordAuthenticationToken toAuthentication(){
        return new UsernamePasswordAuthenticationToken(username, password);
    }
}
