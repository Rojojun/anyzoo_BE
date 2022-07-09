package com.finalproject.breeding.user;

import com.finalproject.breeding.user.dto.requestDto.LoginDto;
import com.finalproject.breeding.user.dto.requestDto.SignupRequestDto;
import com.finalproject.breeding.user.dto.requestDto.TokenRequestDto;
import com.finalproject.breeding.etc.dto.StatusResponseDto;
import com.finalproject.breeding.user.dto.responseDto.TokenDto;
import com.finalproject.breeding.user.dto.responseDto.UserInfo;
import com.finalproject.breeding.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/user/signup")
    public ResponseEntity<Object> signup(@RequestBody SignupRequestDto signupRequestDto) {

        return new ResponseEntity<>(new StatusResponseDto("회원가입이 되었습니다.", userService.signup(signupRequestDto)), HttpStatus.OK);
    }

    @PostMapping("/user/login")
    public ResponseEntity<Object> login(@RequestBody LoginDto loginDto) {
        Map<String, Object> data = userService.login(loginDto);
        return new ResponseEntity<>(new StatusResponseDto("로그인이 되었습니다.", data), HttpStatus.OK);
    }


    //@PatchMapping("/user/edit")
    //public ResponseEntity<UserResponseDto> edit(@RequestBody UserEditDto userEditDto){
    //    userService.edit(userEditDto);
    //    return ResponseEntity.ok(new UserResponseDto("수정 완료 됬다"));
    //}

    //oauth 통신할 api 추가하면 됨.

    @PatchMapping("/user/find/password")
    public void newPW(){

    }

    @PostMapping("/user/reissue")
    public ResponseEntity<Object> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        TokenDto tokenDto = userService.reissue(tokenRequestDto);
        return new ResponseEntity<>(new StatusResponseDto("토큰이 재발급 되었습니다.", tokenDto), HttpStatus.OK);
    }

    @GetMapping("/api/user/userInfo")
    @ResponseBody
    public UserInfo Session() {
       return new UserInfo(userService.getUser());
    }

    //@ExceptionHandler(Exception.class)
    //@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    //public UserResponseDto notFound(Exception e) {
    //    return new UserResponseDto(e.getMessage());
    //}

}
