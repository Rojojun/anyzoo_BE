package com.finalproject.breeding.controller;

import com.finalproject.breeding.dto.*;
import com.finalproject.breeding.model.User;
import com.finalproject.breeding.repository.UserRepository;
import com.finalproject.breeding.security.UserDetailsImpl;
import com.finalproject.breeding.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/user/signup")
    public ResponseEntity<UserResponseDto> signup(@RequestBody UserRequestDto memberRequestDto) {
        userService.signup(memberRequestDto);
        return ResponseEntity.ok(new UserResponseDto("회원가입 됬다"));
    }

    @PostMapping("/user/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody UserRequestDto memberRequestDto) {
        return ResponseEntity.ok(new UserResponseDto(userService.login(memberRequestDto), "로그인 되었습니다"));
    }


    @PatchMapping("/user/edit")
    public ResponseEntity<UserResponseDto> edit(@RequestBody UserEditDto userEditDto){
        userService.edit(userEditDto);
        return ResponseEntity.ok(new UserResponseDto("수정 완료 됬다"));
    }

    //oauth 통신할 api 추가하면 됨.

    @PatchMapping("/user/find/password")
    public void newPW(){

    }

    @PostMapping("/user/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(userService.reissue(tokenRequestDto));
    }

    @GetMapping("/test")
    public String test() {
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       User principal = (User) authentication.getPrincipal();
       String username = principal.getUsername();
       return username;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public UserResponseDto notFound(Exception e) {
        return new UserResponseDto(e.getMessage());
    }

}
