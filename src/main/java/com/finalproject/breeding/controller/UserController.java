package com.finalproject.breeding.controller;

import com.finalproject.breeding.dto.*;
import com.finalproject.breeding.error.StatusResponseDto;
import com.finalproject.breeding.model.User;
import com.finalproject.breeding.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/user/signup")
    public ResponseEntity<Object> signup(@RequestBody SignupRequestDto signupRequestDto) {
        Map<String, Object>data = userService.signup(signupRequestDto);
        return new ResponseEntity<>(new StatusResponseDto("회원가입 되었습니다.", data), HttpStatus.OK);
    }

    //인증 이메일 발송 진행 api
    @GetMapping("/confirm-email")
    public String confirmEmail(@ModelAttribute EmailVerificationRequestDto emailVerificationRequestDto){
        userService.confirmEmail(emailVerificationRequestDto);
        return "인증이 완료되었습니다.";
    }

    @PostMapping("/user/login")
    public ResponseEntity<Object> login(@RequestBody LoginDto loginDto) {
        Map<String, Object> data = userService.login(loginDto);
        return new ResponseEntity<>(new StatusResponseDto("로그인 되었습니다.", data), HttpStatus.OK);
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

    @GetMapping("/user/userInfo")
    @ResponseBody
    public UserInfo Session() {
       User user = userService.getUser();
       return new UserInfo(user.getUsername(), user.getNickname(), user.getImg(), user.getTier(), user.getExp());
    }

    //@ExceptionHandler(Exception.class)
    //@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    //public UserResponseDto notFound(Exception e) {
    //    return new UserResponseDto(e.getMessage());
    //}

}
