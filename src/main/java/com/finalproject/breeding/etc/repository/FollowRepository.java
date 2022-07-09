package com.finalproject.breeding.etc.repository;

import com.finalproject.breeding.etc.dto.FollowResponseDto;
import com.finalproject.breeding.etc.model.Follow;
import com.finalproject.breeding.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Follow findByFollowerAndFollowing(User follower, User following);

    
    List<FollowResponseDto> findFollowingByFollowerOrderByIdDesc(User follower);


    List<FollowResponseDto> findFollowerByFollowingOrderByIdDesc(User following);
}
