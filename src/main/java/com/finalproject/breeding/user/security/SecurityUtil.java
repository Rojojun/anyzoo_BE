package com.finalproject.breeding.user.security;

import com.finalproject.breeding.error.CustomException;
import com.finalproject.breeding.error.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
public class SecurityUtil {

    private SecurityUtil() { }

    // SecurityContext 에 유저 정보가 저장되는 시점
    // Request 가 들어올 때 JwtFilter 의 doFilter 에서 저장
    public static String getCurrentUsername() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            throw  new CustomException(ErrorCode.NOT_FOUND_AUTHORIZATION_IN_SECURITY_CONTEXT);
        }

        return authentication.getName();
    }
}
