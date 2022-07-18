package com.finalproject.breeding.etc.service;

import com.finalproject.breeding.board.dto.PostResponseDto;
import com.finalproject.breeding.board.model.BoardMain;
import com.finalproject.breeding.board.model.category.BoardKind;
import com.finalproject.breeding.board.repository.BoardMainRepository;
import com.finalproject.breeding.board.repository.CommunityRepository;
import com.finalproject.breeding.board.repository.PostRepository;
import com.finalproject.breeding.error.CustomException;
import com.finalproject.breeding.error.ErrorCode;
import com.finalproject.breeding.etc.dto.FollowResponseDto;
import com.finalproject.breeding.etc.dto.MyPagePostResponseDto;
import com.finalproject.breeding.etc.model.Follow;
import com.finalproject.breeding.etc.repository.FollowRepository;
import com.finalproject.breeding.user.User;
import com.finalproject.breeding.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class MyPageService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final FollowRepository followRepository;


    public Slice<MyPagePostResponseDto> getMyPagePost(String nickname, int page) {
        PageRequest pageRequest = PageRequest.of(page, 12);
        return postRepository.findByUserNicknameOrderByBoardMainCreatedAtDesc(pageRequest, nickname);
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
        return data;
    }

    public List<FollowResponseDto> getFollowing(String nickname) {
        User follower = userRepository.findByNickname(nickname).orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND_USER_INFO));
        return followRepository.findFollowingByFollowerOrderByIdDesc(follower);
    }

    public List<FollowResponseDto> getFollower(String nickname) {
        User following = userRepository.findByNickname(nickname).orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND_USER_INFO));
        return followRepository.findFollowerByFollowingOrderByIdDesc(following);
    }

    public boolean checkFollow(User follower, String nickname) {
        User following = userRepository.findByNickname(nickname).orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND_USER_INFO));

        return followRepository.findByFollowerAndFollowing(follower, following) == null;
    }
}
