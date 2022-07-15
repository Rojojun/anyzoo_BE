package com.finalproject.breeding.user;


import com.finalproject.breeding.board.model.Post;
import com.finalproject.breeding.user.dto.requestDto.LoginDto;
import com.finalproject.breeding.user.dto.requestDto.SignupRequestDto;
import com.finalproject.breeding.user.dto.requestDto.TokenRequestDto;
import com.finalproject.breeding.error.CustomException;
import com.finalproject.breeding.error.ErrorCode;
import com.finalproject.breeding.board.model.Community;
import lombok.RequiredArgsConstructor;

import java.util.regex.Pattern;

@RequiredArgsConstructor
public class UserValidator {

    public static void validateInputNickname(SignupRequestDto requestDto) {

        String nickname = requestDto.getNickname();

        String patternNickname = "^[A-Za-z0-9가-힣]{2,6}$";

        // 닉네임 설정 유효성 검사
        if (nickname == null || !Pattern.matches(patternNickname, nickname)) {
            throw new CustomException(ErrorCode.SIGNUP_NICKNAME_WRONG_INPUT);
        }
    }


    public static void validateUsernameEmpty(LoginDto loginDto) {

        String username = loginDto.getUsername();

        if (username.isEmpty()) {
            throw new CustomException(ErrorCode.LOGIN_MEMBERID_EMPTY);
        }
    }

    public static void validatePasswordEmpty(LoginDto loginDto) {

        String password = loginDto.getPassword();

        if (password.isEmpty()) {
            throw new CustomException(ErrorCode.LOGIN_PASSWORD_EMPTY);
        }
    }

    public static void validateRefreshTokenReissue(TokenRequestDto tokenRequestDto) {

        String accessToken = tokenRequestDto.getAccessToken();
        String refreshToken = tokenRequestDto.getRefreshToken();

        if (accessToken == null || refreshToken == null) {
            throw new CustomException(ErrorCode.REFRESH_TOKEN_REISSUE_WRONG_INPUT);
        }
    }
    public static void validateUpdate4User(User user, Long id) {

        Long userId = user.getId();

        if (!userId.equals(id)) {
            throw new CustomException(ErrorCode.POST_UPDATE_WRONG_ACCESS);
        }
    }
    public static void validateDelete4User(User user, Long id) {

        Long userId = user.getId();

        if (!userId.equals(id)) {
            throw new CustomException(ErrorCode.POST_DELETE_WRONG_ACCESS);
        }
    }

}