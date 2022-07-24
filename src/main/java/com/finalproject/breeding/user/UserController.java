package com.finalproject.breeding.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.finalproject.breeding.dto.EmailVerificationRequestDto;
import com.finalproject.breeding.dto.NewPasswordDto;
import com.finalproject.breeding.dto.PhoneVerificationDto;
import com.finalproject.breeding.dto.UserResponseDto;
import com.finalproject.breeding.user.dto.requestDto.LoginDto;
import com.finalproject.breeding.user.dto.requestDto.SignupRequestDto;
import com.finalproject.breeding.user.dto.requestDto.TokenRequestDto;
import com.finalproject.breeding.etc.dto.response.StatusResponseDto;
import com.finalproject.breeding.user.dto.responseDto.TokenDto;
import com.finalproject.breeding.user.dto.responseDto.UserInfo;
import com.finalproject.breeding.user.service.UserService;
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

    @GetMapping("/user/send/phoneVerification/{phoneNumber}")
    public ResponseEntity<String> sendSMS(@PathVariable String phoneNumber){
        userService.certifyPhoneNumber(phoneNumber);
        return new ResponseEntity<>("인증번호를 발송했습니다", HttpStatus.OK);
    }

    //인증: 문자인증번호 확인
    @PostMapping("/user/confirm/phoneVerification")
    public boolean compareConfirmNumbers(@RequestBody PhoneVerificationDto phoneVerificationDto){
        return userService.compareConfirmNumber(phoneVerificationDto);
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

        return new ResponseEntity<>(new StatusResponseDto("회원가입이 되었습니다.", userService.signup(signupRequestDto)), HttpStatus.OK);
    }

    @PostMapping("/user/login")
    public ResponseEntity<Object> login(@RequestBody LoginDto loginDto) {
        Map<String, Object> data = userService.login(loginDto);
        return new ResponseEntity<>(new StatusResponseDto("로그인이 되었습니다.", data), HttpStatus.OK);
    }

    //Google oauth api
    @GetMapping("/user/oauth/google")
    public ResponseEntity<UserResponseDto> socialLogin(@RequestHeader("code") String code){
        return ResponseEntity.ok(new UserResponseDto(userService.googleLogin(code), "로그인 되었습니다"));
    }

    //Kakao oauth api
    @GetMapping("/user/oauth/kakao")
    public ResponseEntity<UserResponseDto> kakaoLogin(@RequestParam String code) throws JsonProcessingException {
        return ResponseEntity.ok(new UserResponseDto(userService.kakaoLogin(code), "로그인 되었습니다"));
    }


    //----------------------------유저 정보 수정 관련-------------------------------
    @PatchMapping("/user/edit")
    public ResponseEntity<Object> edit(@RequestBody UserEditDto userEditDto){
        Map<String, Object> data = userService.edit(userEditDto);
        return new ResponseEntity<>(new StatusResponseDto("수정 되었습니다..", data), HttpStatus.OK);
    }

    @PostMapping("/user/make/newPassword")
    public ResponseEntity<String> changePassword(@RequestBody NewPasswordDto newPasswordDto){
        userService.changePassword(newPasswordDto);
        return ResponseEntity.ok("비밀번호가 변경되었습니다");
    }

    //잃어버린 Username(email) 폰번호로 찾기
    @GetMapping("/user/find/lostEmail/{phoneNumber}")
    public String findLostEmail(@PathVariable String phoneNumber) {
        return userService.findLostEmail(phoneNumber);
    }

    //----------------------------유저 정보 조회-------------------------------
    @GetMapping("/api/user/userInfo")
    @ResponseBody
    public UserInfo Session() {
       return new UserInfo(userService.getUser());
    }

    @GetMapping("/api/user/userInfoTest")
    @ResponseBody
    public User getuser() {
        return userService.getUser();
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
