package com.finalproject.breeding.controller;

import com.finalproject.breeding.dto.*;
import com.finalproject.breeding.error.StatusResponseDto;
import com.finalproject.breeding.model.User;
import com.finalproject.breeding.service.UserService;
import com.finalproject.breeding.token.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;


@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    //----------------------------유저 정보 중복확인-------------------------------
    //유저네임(이메일) 중복확인
    @GetMapping("/user/checkUsername/{username}")
    public ResponseEntity<Boolean> checkUsernameDuplication(@PathVariable String username){
        return ResponseEntity.ok(userService.checkUsernameDuplication(username));
    }

    //닉네임 중복확인
    @GetMapping("/user/checkNickname/{nickname}")
    public ResponseEntity<Boolean> checkNicknameDuplication(@PathVariable String nickname){
        return ResponseEntity.ok(userService.checkNicknameDuplication(nickname));
    }

    //폰번호 중복확인
    @GetMapping("/user/checkPhoneNumber/{phoneNumber}")
    public ResponseEntity<Boolean> checkPhoneNumberDuplication(@PathVariable String phoneNumber){
        return ResponseEntity.ok(userService.checkPhoneNumberDuplication(phoneNumber));
    }

    //----------------------------유저 인증 관련-------------------------------
    //인증: 문자인증번호 전송
    @GetMapping("/user/send/phoneVerification")
    public void sendSMS(String phoneNumber){
        userService.certifiedPhoneNumber(phoneNumber);
    }

    //인증: 문자인증번호 확인
    @PostMapping("/user/confirm/phoneVerification")
    public boolean compareConfirmNumbers(String phoneNumber, String numStr){
        return userService.compareConfirmNumber(phoneNumber, numStr);
    }

    //인증: 이메일 인증 링크 전송
    @GetMapping("/user/send/emailVerification")
    public void sendEmailVerificationLink(String email){
        userService.sendEmailVerificationLink(email);
    }

    //인증: 이메일 인증확인 (유저가 인증 이메일 링크를 타면 이 메서드로 옴)
    @GetMapping("/user/confirm/emailVerification")
    public boolean confirmEmailVerification(@ModelAttribute EmailVerificationRequestDto emailVerificationRequestDto){
        return userService.confirmEmail(emailVerificationRequestDto);
    }


    //----------------------------유저 로그인, 가입-------------------------------
    @PostMapping("/user/signup")
    public ResponseEntity<Object> signup(@RequestBody SignupRequestDto signupRequestDto) {
        Map<String, Object>data = userService.signup(signupRequestDto);
        return new ResponseEntity<>(new StatusResponseDto("회원가입 되었습니다.", data), HttpStatus.OK);
    }

    @PostMapping("/user/login")
    public ResponseEntity<Object> login(@RequestBody LoginDto loginDto) {
        Map<String, Object> data = userService.login(loginDto);
        return new ResponseEntity<>(new StatusResponseDto("로그인 되었습니다.", data), HttpStatus.OK);
    }



    //Google oauth 통신할 api
    @PostMapping("/user/socialLogin")
    public ResponseEntity<UserResponseDto> socialLogin(@RequestHeader("code") String code){
        return ResponseEntity.ok(new UserResponseDto(userService.socialLogin(code), "로그인 되었습니다"));
    }


    //----------------------------유저 정보 수정 관련-------------------------------
    //@PatchMapping("/user/edit")
    //public ResponseEntity<UserResponseDto> edit(@RequestBody UserEditDto userEditDto){
    //    userService.edit(userEditDto);
    //    return ResponseEntity.ok(new UserResponseDto("수정 완료 됬다"));
    //}

    //잃어버린 비밀번호 변경
    @PostMapping("/user/make/newPassword")
    public void changePassword(@RequestBody NewPasswordDto newPasswordDto){
        userService.changePassword(newPasswordDto);
    }

    //잃어버린 Username(email) 폰번호로 찾기
    @GetMapping("/user/find/lostEmail")
    public String findLostEmail(String phoneNumber){
        return userService.findLostEmail(phoneNumber);
    }


    //----------------------------유저 정보 조회-------------------------------
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

    //토큰 재발급
    @PostMapping("/user/reissue")
    public ResponseEntity<Object> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        TokenDto tokenDto = userService.reissue(tokenRequestDto);
        return new ResponseEntity<>(new StatusResponseDto("토큰이 재발급 되었습니다.", tokenDto), HttpStatus.OK);
    }

}
