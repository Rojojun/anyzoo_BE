package com.finalproject.breeding.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.breeding.dto.*;
import com.finalproject.breeding.image.model.UserImage;
import com.finalproject.breeding.image.repository.UserImageRepository;
import com.finalproject.breeding.service.VerificationEmailSenderService;
import com.finalproject.breeding.user.*;
import com.finalproject.breeding.user.dto.requestDto.LoginDto;
import com.finalproject.breeding.user.dto.requestDto.ProfileImageDto;
import com.finalproject.breeding.user.dto.requestDto.SignupRequestDto;
import com.finalproject.breeding.user.dto.requestDto.TokenRequestDto;
import com.finalproject.breeding.user.dto.responseDto.TokenDto;
import com.finalproject.breeding.error.CustomException;
import com.finalproject.breeding.error.ErrorCode;
import com.finalproject.breeding.etc.model.RefreshToken;
import com.finalproject.breeding.user.dto.responseDto.UserInfo;
import com.finalproject.breeding.user.repository.RefreshTokenRepository;
import com.finalproject.breeding.user.repository.UserRepository;
import com.finalproject.breeding.user.security.UserDetailsImpl;
import com.finalproject.breeding.user.token.TokenProvider;
import com.finalproject.breeding.socialUtil.GoogleRestTemplate;
import lombok.RequiredArgsConstructor;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.apache.http.protocol.HTTP;
import org.apache.tomcat.jni.Local;
import org.json.simple.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserImageRepository userImageRepository;

    //이메일 인증 발송 의존성 추가가
    private final VerificationEmailSenderService verificationEmailSenderService;

    //잠시동안 저장할 유저 핸드폰 번호와 인증번호
    public static HashMap<String,String> phoneVerificationDB = new HashMap<>();

    //인증코드드
    public static HashMap<String, LocalDateTime> verificationDuration = new HashMap<>();

    //잠시동안 저장할 유저 이메일과 이메일 authToken
    public static HashMap<String,String> emailVerificationDB = new HashMap<>();

    //Google Social Login
    private final GoogleRestTemplate googleRestTemplate;


    //----------------------------유저 정보 중복 관련-------------------------------
    //이메일 중복확인
    @Transactional(readOnly = true)
    public boolean checkUsernameDuplication(String username){
        return userRepository.existsByUsername(username);
    }

    //닉네임 중복확인
    @Transactional(readOnly = true)
    public boolean checkNicknameDuplication(String nickname){
        return userRepository.existsByNickname(nickname);
    }

    //폰번호 중복확인
    @Transactional(readOnly = true)
    public boolean checkPhoneNumberDuplication(String phoneNumber){
        return userRepository.existsByPhoneNumber(phoneNumber);
    }


    //----------------------------유저 인증 관련-------------------------------
    //폰번호 문자인증번호 발송
    public void certifyPhoneNumber(String phoneNumber){
        String api_key = "NCS1HI0WXQRNU4EA";
        String api_secret = "0SNTCCSSJJSUPIAOTBTTF0ILPH8QNOYG";
        Message coolsms = new Message(api_key, api_secret);

        Random rand = new Random();
        String numStr = "";
        for(int i = 0; i < 4; i++){
            String ran = Integer.toString(rand.nextInt(10));
            numStr += ran;
        }

        System.out.println("수신자 번호: " + phoneNumber);
        System.out.println("인증 번호: " + numStr);
        phoneVerificationDB.put(phoneNumber, numStr);
        verificationDuration.put(phoneNumber, LocalDateTime.now());

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", phoneNumber); //수신전화번호
        params.put("from", "01083231544"); //발신전화번호
        params.put("type", "SMS");
        params.put("text", "ANYZOO 인증번호는" + "[" + numStr + "]" + "입니다.");
        params.put("app_version", "test app 1.2");

        try{
            JSONObject obj = coolsms.send(params);
            System.out.println(obj.toString());
        }catch (CoolsmsException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    //폰번호 문자인증번호 확인 메소드
    public boolean compareConfirmNumber(PhoneVerificationDto phoneVerificationDto){
        String tests = phoneVerificationDB.get(phoneVerificationDto.getPhoneNumber());
        if(Optional.ofNullable(tests).isPresent()){
            LocalDateTime timeNow = LocalDateTime.now();
            LocalDateTime beginningTime = verificationDuration.get(phoneVerificationDto.getPhoneNumber());
            Duration duration = Duration.between(timeNow, beginningTime);
            if(tests.equals(phoneVerificationDto.getNumStr())){
                if(duration.getSeconds() > 181){
                    phoneVerificationDB.remove(phoneVerificationDto.getPhoneNumber());
                    verificationDuration.remove(phoneVerificationDto.getPhoneNumber());
                    return false;
                }else{
                    phoneVerificationDB.remove(phoneVerificationDto.getPhoneNumber());
                    verificationDuration.remove(phoneVerificationDto.getPhoneNumber());
                    return true;
                }
            }else{
                return false;
            }
        }
        return false;
    }

    //이메일 인증 링크 발송
    @Transactional
    public void sendEmailVerificationLink(String email){
        //이메일 인증 UUID 생성
        String userUUID = UUID.randomUUID().toString();
        //유저의 이메일과 UUID 저장
        emailVerificationDB.put(email, userUUID);
        //이메일 인증 이메일 발송
        verificationEmailSenderService.send(email, userUUID);
    }

    //이메일 인증 확인 메서드
    @Transactional
    public boolean confirmEmail(EmailVerificationRequestDto emailVerificationRequestDto){
        String authToken = emailVerificationDB.get(emailVerificationRequestDto.getEmail());
        if(Optional.ofNullable(authToken).isPresent()){
            if(authToken.equals(emailVerificationRequestDto.getAuthToken())){
                emailVerificationDB.remove(emailVerificationRequestDto.getEmail());
                return true;
            }
        }
        return false;
    }


    //----------------------------유저 로그인/가입 관련-------------------------------
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

        // 폰번호 중복 확인
        String phoneNumber = signupRequestDto.getPhoneNumber();
        if(userRepository.existsByPhoneNumber(phoneNumber)){
            throw new CustomException(ErrorCode.SIGNUP_PHONENUMBER_DUPLICATE_CHECK);
        }

        // 유저 이미지 등록

        UserImage userImage;
        if (signupRequestDto.getUserImage()==null){
            userImageRepository.save(userImage = new UserImage());
        } else {
            userImage = userImageRepository.findById(signupRequestDto.getUserImage()).orElseThrow(()->new CustomException(ErrorCode.Image_NOT_FOUND));
        }
        userImage.updateToUser(userRepository.save(
                User.builder()
                        .username(signupRequestDto.getUsername())
                        .password(passwordEncoder.encode(signupRequestDto.getPassword()))
                        .nickname(signupRequestDto.getNickname())
                        .userImage(userImage)
                        .verification(true)
                        .phoneNumber(signupRequestDto.getPhoneNumber())
                        .userRole(UserRole.ROLE_USER)
                        .registerType(RegisterType.GENERAL)
                        .build()
        ));

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
    public Map<String, Object> login(LoginDto loginDto) {
        UserValidator.validateUsernameEmpty(loginDto);
        UserValidator.validatePasswordEmpty(loginDto);

        User user = userRepository.findByUsername(loginDto.getUsername()).orElseThrow(
                () -> new CustomException(ErrorCode.LOGIN_NOT_FOUNT_MEMBERID)
        );

        //이메일 인증 계정 확인 알고리즘
        if(!user.isVerification()){
            throw new CustomException(ErrorCode.NOT_VERIFIED_USER_INFORMATION);
        }

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

    //Google 로그인
    @Transactional
    public SocialTokenDto googleLogin(String code) {
        SocialLoginRequestDto socialLoginRequestDto = googleRestTemplate.googleUserInfoByAccessToken(googleRestTemplate.findAccessTokenByCode(code).getAccess_token());
        User user = userRepository.findByUsername(socialLoginRequestDto.getEmail())
                .orElseGet(() -> {
                            User tempUser = userRepository.save(new User(socialLoginRequestDto));
                            UserImage userImage = userImageRepository.save(new UserImage(tempUser));
                            tempUser.updateProfileImage(userImage);
                            return tempUser;
                        }
                );
        return createToken(new UserRequestDto(user));
    }

    @Transactional
    public SocialTokenDto kakaoLogin(String code) throws JsonProcessingException {
        // 1. "인가 코드"로 "액세스 토큰" 요청
        String kakaoAccessToken = getAccessToken(code);
        // 2. 토큰으로 카카오 API 호출
        KakaoUserInfoDto kakaoUserInfo = getKakaoUserInfo(kakaoAccessToken);

        // DB 에 중복된 Kakao Id 가 있는지 확인
        Long kakaoId = kakaoUserInfo.getKakaoId();
        User kakaoUser = userRepository.findByKakaoId(kakaoId)
                .orElse(null);
        if (kakaoUser == null) {
        // role: 일반 사용자
            UserRole role = UserRole.ROLE_USER;
            RegisterType registerType = RegisterType.SOCIAL;
            kakaoUser = new User(role, registerType ,kakaoId, getSocialRandomValue("kakaoUser"));

            User user = userRepository.save(kakaoUser);

            UserImage userImage = userImageRepository.save(new UserImage(user, kakaoUserInfo));

            user.updateProfileImage(userImage);
        }
        return createToken(new UserRequestDto(kakaoUser.getUsername()));
    }


    public String getSocialRandomValue(String provider) {
        while (true) {
            String value = provider + UUID.randomUUID().toString().replaceAll("-", "").substring(0, 5);
            if (!userRepository.existsByUsername(value) && !userRepository.existsByNickname(value)) {
                return value;
            }
        }
    }

    public SocialTokenDto createToken(UserRequestDto userRequestDto){
        SocialTokenDto tokenDto = tokenProvider.socialLoginTokenCreate(userRequestDto);

        tokenDto.setUsername(userRequestDto.getUsername());

        User user = userRepository.findByUsername(userRequestDto.getUsername()).orElse(null);
        assert user != null;
        tokenDto.setNickname(user.getNickname());

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(userRequestDto.getUsername())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        // 5. 토큰 발급
        return tokenDto;
    }

    //kakao 인가 code 받은후 access token 생성
    private String getAccessToken(String code) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

// HTTP Body 생성
            StringBuilder body = new StringBuilder();
            body.append("grant_type=authorization_code&client_id=7ed074dd8ee05fafa99735fba28a41d2&redirect_uri=https://anyzoo.co.kr/oauth&code=" + code + "&client_secret=28ibusk6KsYMwzHk2MyKow5ed5wV8j8l");
//https://anyzoo.co.kr/oauth

// HTTP 요청 보내기
            HttpEntity<String> kakaoTokenRequest =
                    new HttpEntity<String>(body.toString(), headers);
            RestTemplate rt = new RestTemplate();
            ResponseEntity<String> response = rt.exchange(
                    "https://kauth.kakao.com/oauth/token",
                    HttpMethod.POST,
                    kakaoTokenRequest,
                    String.class
            );

// HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();
    }

    //kakao 유저정보 메서드
    private KakaoUserInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {

        HttpHeaders headers = new HttpHeaders();

        // HTTP Header 생성
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

// HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String>response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        Long id = jsonNode.get("id").asLong();
        String profile_image = jsonNode.get("kakao_account").get("profile").get("profile_image_url").asText();

        System.out.println("카카오 사용자 정보: " + id + ", " + profile_image);
        return new KakaoUserInfoDto(id, profile_image);
    }

    //----------------------------유저 정보 수정 관련-------------------------------
    //잃어버린 비밀번호 변경
    @Transactional
    public void changePassword(NewPasswordDto newPasswordDto){
        User user = userRepository
                .findByPhoneNumber(newPasswordDto.getPhoneNumber())
                .orElseThrow(() -> new CustomException(ErrorCode.OK_BUT_NO_USER));
        String newPassword = passwordEncoder.encode(newPasswordDto.getNewPassword());
        user.changePassword(newPassword);
    }

    //잃어버린 Username(email) 찾기
    @Transactional
    public String findLostEmail(String phoneNumber){
            User user = userRepository
                    .findByPhoneNumber(phoneNumber)
                    .orElseGet(() -> null);
                    //.orElseThrow(() -> new IllegalArgumentException("번호가 일치하지 않습니다"));
                    //.orElseThrow(() -> new CustomException(ErrorCode.OK_BUT_NO_USER));
            if(!Optional.ofNullable(user).isPresent()) {
                return "존재하지 않는 유저 정보 입니다";
            }
            String[] usernameSplit = user.getUsername().split("@");
            if (usernameSplit[0].length() <= 2) {
                return user.getUsername().charAt(0) + "*****@" + usernameSplit[1];
            } else {
                return user.getUsername().substring(0, 2) + "****@" + usernameSplit[1];
            }
    }

    @Transactional
    public Map<String, Object> editUserInfo(UserEditDto userEditDto) {
        User user = getUser();
        if(Optional.ofNullable(userEditDto.getNickname()).isPresent()) {
            user.editUserNickname(userEditDto.getNickname());
        }
        if(Optional.ofNullable(userEditDto.getPhoneNumber()).isPresent()){
            user.editUserPhoneNumber(userEditDto.getPhoneNumber());
        }
        if(Optional.ofNullable(userEditDto.getPassword()).isPresent()){
            user.changePassword(passwordEncoder.encode(userEditDto.getPassword()));
        }
        //        if(Optional.ofNullable(userEditDto.getNewPassword()).isPresent()){
//            if(passwordEncoder.matches(userEditDto.getOldPassword(), user.getPassword())){
//                user.changePassword(passwordEncoder.encode(userEditDto.getNewPassword()));
//            }else{
//                throw new CustomException(ErrorCode.PASSWORDS_NOT_MATCH);
//            }
//        }
        Map<String, Object> data = new HashMap<>();
        data.put("nickname", new UserInfo(user));
        return data;
    }

    @Transactional
    public Map<String, Object> editUserImage(ProfileImageDto profileImageDto){
        User user = getUser();

        UserImage userImage;
        if (profileImageDto.getUserImage()==null){
            userImageRepository.save(userImage = new UserImage());
        } else {
            userImage = userImageRepository.findById(profileImageDto.getUserImage()).orElseThrow(()->new CustomException(ErrorCode.Image_NOT_FOUND));
        }
        user.updateProfileImage(userImage);
        userImage.updateToUser(user);

        Map<String, Object> data = new HashMap<>();
        data.put("nickname", new UserInfo(user));
        return data;
    }

    @Transactional
    public TokenDto reissue(TokenRequestDto tokenRequestDto){
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


    //----------------------------유저 조회 관련-------------------------------
    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(authentication.getName()).orElseThrow(
                () -> new UsernameNotFoundException("존재하지 않는 유저입니다")
        );
    }

    public User getUserObject(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("존재하지 않는 유저입니다")
        );
    }
}
