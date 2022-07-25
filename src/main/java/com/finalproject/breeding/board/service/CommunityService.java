package com.finalproject.breeding.board.service;

import com.finalproject.breeding.board.dto.CommunityRequestDto;
import com.finalproject.breeding.board.dto.CommunityResponseDto;
import com.finalproject.breeding.board.model.BoardMain;
import com.finalproject.breeding.board.model.Community;
import com.finalproject.breeding.board.model.Post;
import com.finalproject.breeding.board.model.category.CommunityCategory;
import com.finalproject.breeding.board.model.category.PostNReelsCategory;
import com.finalproject.breeding.board.repository.BoardMainRepository;
import com.finalproject.breeding.board.repository.CommunityRepository;
import com.finalproject.breeding.error.CustomException;
import com.finalproject.breeding.error.ErrorCode;
import com.finalproject.breeding.image.AwsS3Service;
import com.finalproject.breeding.image.ImageRequestDto;
import com.finalproject.breeding.image.model.CommunityImage;
import com.finalproject.breeding.image.model.PostImage;
import com.finalproject.breeding.image.repository.CommunityImageRepository;
import com.finalproject.breeding.user.User;
import com.finalproject.breeding.user.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CommunityService {

    private final CommunityRepository communityRepository;
    private final BoardMainRepository boardMainRepository;
    private final AwsS3Service awsS3Service;


    public Map<String, Object> registCommunity(CommunityRequestDto communityRequestDto, User user) {
        Community community;
        if(communityRequestDto.getCommunityImages()!=null){
            List<CommunityImage> communityImages = communityRequestDto.getCommunityImages();

            community = Community.builder().communityImages(communityImages).user(user).boardMain(boardMainRepository.save(new BoardMain(communityRequestDto))).build();

            imageUpdateToCommunity(communityImages, communityRepository.save(community));
        }else{
            community = Community.builder().boardMain(boardMainRepository.save(new BoardMain(communityRequestDto))).user(user).build();
            communityRepository.save(community);
        }



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

    public CommunityResponseDto getCommunityDetail(Long boardMainId) {
        return communityRepository.findByBoardMainId(boardMainId);
    }


    public Slice<CommunityResponseDto> readCommunity(int page) {
        PageRequest pageRequest = PageRequest.of(page, 5);
        return communityRepository.findByOrderByBoardMainCreatedAtDesc(pageRequest);
    }

    public void deleteCommunity(User user, Long boardMainId) {
        Community community = communityRepository.findCommunityByBoardMainId(boardMainId);
        UserValidator.validateDelete4User(user, community.getUser().getId());
        awsS3Service.removeCommunityImages(community.getId());
        communityRepository.delete(community);
    }

    public Map<String, Object> updateCommunity(Long boardMainId, CommunityRequestDto communityRequestDto, User user) {
        Community community = communityRepository.findCommunityByBoardMainId(boardMainId);
        UserValidator.validateUpdate4User(user, community.getUser().getId());
        community.getBoardMain().updateCommunity(communityRequestDto);
        communityRepository.save(community);

        Map<String, Object> data = new HashMap<>();
        data.put("communityId", community.getId());
        data.put("boardMainId", community.getBoardMain().getId());
        return data;
    }
}
