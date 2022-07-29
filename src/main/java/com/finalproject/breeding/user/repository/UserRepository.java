package com.finalproject.breeding.user.repository;

import com.finalproject.breeding.user.User;
import com.finalproject.breeding.user.dto.responseDto.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByPhoneNumber(String phoneNumber);

    Optional<User>findByKakaoId(Long kakaoId);

    boolean existsByUsername(String username);

    boolean existsByNickname(String nickname);
    Optional<User> findByNickname(String nickname);
    boolean existsByPhoneNumber(String phoneNumber);

    UserInfo findUserByNickname(String nickname);
}
