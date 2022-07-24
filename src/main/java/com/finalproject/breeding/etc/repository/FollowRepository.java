package com.finalproject.breeding.etc.repository;

import com.finalproject.breeding.etc.dto.response.FollowerDto;
import com.finalproject.breeding.etc.dto.response.FollowingDto;
import com.finalproject.breeding.etc.model.Follow;
import com.finalproject.breeding.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Follow findByFollowerAndFollowing(User follower, User following);

    
    List<FollowingDto> findFollowByFollowerOrderByIdDesc(User follower);

    List<FollowerDto> findFollowByFollowingOrderByIdDesc(User following);
}
