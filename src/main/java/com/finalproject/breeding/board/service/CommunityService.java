package com.finalproject.breeding.board.service;

import com.finalproject.breeding.board.dto.CommunityRequestDto;
import com.finalproject.breeding.board.dto.CommunityResponseDto;
import com.finalproject.breeding.board.model.BoardMain;
import com.finalproject.breeding.board.model.Community;
import com.finalproject.breeding.board.repository.BoardMainRepository;
import com.finalproject.breeding.board.repository.CommunityRepository;
import com.finalproject.breeding.error.CustomException;
import com.finalproject.breeding.error.ErrorCode;
import com.finalproject.breeding.image.model.CommunityImage;
import com.finalproject.breeding.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommunityService {

    private final CommunityRepository communityRepository;
    private final BoardMainRepository boardMainRepository;


    public Map<String, Object> registCommunity(CommunityRequestDto communityRequestDto, User user) {
        List<CommunityImage> communityImages = communityRequestDto.getCommunityImages();

        Community community = new Community(communityRequestDto,boardMainRepository.save(new BoardMain(communityRequestDto)),user, communityImages);

        imageUpdateToCommunity(communityImages, communityRepository.save(community));

        Map<String, Object> data = new HashMap<>();
        data.put("communityId", community.getId());
        data.put("boardMainId", community.getBoardMain().getId());
        return data;
    }



    public void imageUpdateToCommunity(List<CommunityImage> communityImages, Community community) {
        for (CommunityImage communityImage : communityImages) {
            communityImage.updateToCommunity(community);
        }
    }

    public CommunityResponseDto getCommunityDetail(Long communityId) {
        Community community = communityRepository.findById(communityId).orElseThrow(()->new CustomException(ErrorCode.POST_NOT_FOUND));
        return new CommunityResponseDto(community);
    }


}
