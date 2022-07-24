package com.finalproject.breeding.etc.service;

import com.finalproject.breeding.board.dto.CommunityResponseDto;
import com.finalproject.breeding.board.repository.CommunityRepository;
import com.finalproject.breeding.board.repository.PostRepository;
import com.finalproject.breeding.board.repository.ReelsRepository;
import com.finalproject.breeding.error.CustomException;
import com.finalproject.breeding.error.ErrorCode;
import com.finalproject.breeding.etc.dto.response.FollowerDto;
import com.finalproject.breeding.etc.dto.response.FollowingDto;
import com.finalproject.breeding.etc.dto.response.MyPagePostDto;
import com.finalproject.breeding.etc.dto.response.MyPageReelsDto;
import com.finalproject.breeding.etc.model.Follow;
import com.finalproject.breeding.etc.repository.FollowRepository;
import com.finalproject.breeding.user.User;
import com.finalproject.breeding.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class MyPageService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final ReelsRepository reelsRepository;
    private final CommunityRepository communityRepository;

    @Transactional(readOnly = true)
    public Slice<MyPagePostDto> getMyPagePost(String nickname, int page) {
        PageRequest pageRequest = PageRequest.of(page, 16);
        return postRepository.findByUserNicknameOrderByBoardMainCreatedAtDesc(pageRequest, nickname);
    }

    @Transactional(readOnly = true)
    public Slice<MyPageReelsDto> getMyPageReels(String nickname, int page) {
        PageRequest pageRequest = PageRequest.of(page, 16);
        return reelsRepository.findByUserNicknameOrderByBoardMainCreatedAtDesc(pageRequest, nickname);
    }

    @Transactional(readOnly = true)
    public Slice<CommunityResponseDto> getMyPageCommunity(String nickname, int page) {
        PageRequest pageRequest = PageRequest.of(page, 5);
        return communityRepository.findByUserNicknameOrderByBoardMainCreatedAtDesc(pageRequest, nickname);
    }

    public Map<String, Object> followUnFollow(User follower, String nickname) {
        User following = userRepository.findByNickname(nickname).orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND_USER_INFO));
        Map<String, Object> data = new HashMap<>();
        if (followRepository.findByFollowerAndFollowing(follower, following)==null){
            followRepository.save(new Follow(follower, following));
            follower.follower(follower);
            following.following(following);
            data.put("follow", nickname);
        } else {
            Follow follow = followRepository.findByFollowerAndFollowing(follower, following);
            followRepository.delete(follow);
            follower.unFollower(follower);
            following.unFollowing(following);
            data.put("unFollow", nickname);
        }
        userRepository.save(follower);
        userRepository.save(following);
        return data;
    }

    public List<FollowingDto> getFollowing(String nickname) {
        User follower = userRepository.findByNickname(nickname).orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND_USER_INFO));
        return followRepository.findFollowByFollowerOrderByIdDesc(follower);
    }

    public List<FollowerDto> getFollower(String nickname) {
        User following = userRepository.findByNickname(nickname).orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND_USER_INFO));
        return followRepository.findFollowByFollowingOrderByIdDesc(following);
    }

    public boolean checkFollow(User follower, String nickname) {
        User following = userRepository.findByNickname(nickname).orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND_USER_INFO));

        return followRepository.findByFollowerAndFollowing(follower, following) == null;
    }



}
