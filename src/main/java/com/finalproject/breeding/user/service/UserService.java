package com.finalproject.breeding.user.service;

import com.finalproject.breeding.image.model.UserImage;
import com.finalproject.breeding.image.repository.UserImageRepository;
import com.finalproject.breeding.user.UserValidator;
import com.finalproject.breeding.user.dto.requestDto.LoginDto;
import com.finalproject.breeding.user.dto.requestDto.SignupRequestDto;
import com.finalproject.breeding.user.dto.requestDto.TokenRequestDto;
import com.finalproject.breeding.user.UserEditDto;
import com.finalproject.breeding.user.dto.responseDto.TokenDto;
import com.finalproject.breeding.error.CustomException;
import com.finalproject.breeding.error.ErrorCode;
import com.finalproject.breeding.etc.model.RefreshToken;
import com.finalproject.breeding.user.User;
import com.finalproject.breeding.user.UserRole;
import com.finalproject.breeding.user.repository.RefreshTokenRepository;
import com.finalproject.breeding.user.repository.UserRepository;
import com.finalproject.breeding.user.SecurityUtil;
import com.finalproject.breeding.user.token.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserImageRepository userImageRepository;

    @Transactional
    public Map<String, Object> signup(SignupRequestDto signupRequestDto) {


        // 회원 아이디 중복 확인
        String username = signupRequestDto.getUsername();
        if (userRepository.existsByUsername(username)) {
            throw new CustomException(ErrorCode.SIGNUP_MEMBERID_DUPLICATE_CHECK);
        }

        // 닉네임 중복 확인
        String nickname = signupRequestDto.getNickname();
        if (userRepository.existsByNickname(nickname)) {
            throw new CustomException(ErrorCode.SIGNUP_NICKNAME_DUPLICATE_CHECK);
        }

        UserImage userImage;
        if (signupRequestDto.getUserImage()==null){
            userImageRepository.save(userImage = new UserImage());
        } else {
            userImage = signupRequestDto.getUserImage();
        }
        userImage.updateToUser(userRepository.save(
                User.builder()
                        .username(signupRequestDto.getUsername())
                        .password(passwordEncoder.encode(signupRequestDto.getPassword()))
                        .nickname(signupRequestDto.getNickname())
                        .userImage(userImage)
                        .userRole(UserRole.ROLE_USER)
                        .build()));


        //JWT 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authenticationManagerBuilder.getObject().authenticate(signupRequestDto.toAuthentication()));

        // 4. RefreshToken 저장
        refreshTokenRepository.save(RefreshToken.builder()
                .key(signupRequestDto.getUsername())
                .value(tokenDto.getRefreshToken())
                .build()
        );
        // 5. 토큰 반환
        Map<String, Object> data = new HashMap<>();
        data.put("token", tokenDto);
        return data;
    }

    @Transactional
    public void edit(UserEditDto userEditDto) {
        User user = userRepository
                .findByUsername(SecurityUtil.getCurrentUsername())
                .orElseThrow(() ->new CustomException(ErrorCode.NOT_FOUND_USER_INFO));
        user.edit(userEditDto);
    }

    @Transactional
    public Map<String, Object> login(LoginDto loginDto) {
        UserValidator.validateUsernameEmpty(loginDto);
        UserValidator.validatePasswordEmpty(loginDto);

        User user = userRepository.findByUsername(loginDto.getUsername()).orElseThrow(
                () -> new CustomException(ErrorCode.LOGIN_NOT_FOUNT_MEMBERID)
        );

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.LOGIN_PASSWORD_NOT_MATCH);
        }
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = loginDto.toAuthentication();

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 UserDetailsServiceImpl 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 4. RefreshToken 저장
        refreshTokenRepository.save(RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build()
        );

        // 5. 토큰 반환
        Map<String, Object> data = new HashMap<>();
        data.put("token", tokenDto);

        return data;
    }

    @Transactional
    public TokenDto reissue(TokenRequestDto tokenRequestDto) {
        // 토큰값 제대로 받았는지 확인
        UserValidator.validateRefreshTokenReissue(tokenRequestDto);

        // 1. Refresh Token 만료된 경우
        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new CustomException(ErrorCode.REFRESH_TOKEN_EXPIRED);
        }

        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_USER_INFO)
        );
        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(user.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new CustomException(ErrorCode.REFRESH_TOKEN_NOT_MATCH);
        }

        // 5. 새로운 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 6. 저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // 토큰 발급
        return tokenDto;
    }

    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(authentication.getName()).orElseThrow(
                () -> new UsernameNotFoundException("존재하지 않는 유저입니다")
        );
    }

}
