package com.finalproject.breeding.board.service;

import com.finalproject.breeding.board.dto.CommunityRequestDto;
import com.finalproject.breeding.board.dto.CommunityResponseDto;
import com.finalproject.breeding.board.model.BoardMain;
import com.finalproject.breeding.board.model.Community;
import com.finalproject.breeding.board.model.category.CommunityCategory;
import com.finalproject.breeding.board.repository.BoardMainRepository;
import com.finalproject.breeding.board.repository.CommunityRepository;
import com.finalproject.breeding.image.AwsS3Service;
import com.finalproject.breeding.image.model.CommunityImage;
import com.finalproject.breeding.image.repository.CommunityImageRepository;
import com.finalproject.breeding.user.User;
import com.finalproject.breeding.user.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CommunityService {

    private final CommunityRepository communityRepository;
    private final BoardMainRepository boardMainRepository;
    private final CommunityImageRepository communityImageRepository;
    private final AwsS3Service awsS3Service;


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

    public CommunityResponseDto getCommunityDetail(Long boardMainId) {
        return communityRepository.findByBoardMainId(boardMainId);
    }


    public Slice<CommunityResponseDto> readCommunity(int page, String category) {
        PageRequest pageRequest = PageRequest.of(page, 5);
        switch (category) {
            case "qna":
                return communityRepository.findByCommunityCategoryOrderByBoardMainCreatedAtDesc(pageRequest, CommunityCategory.QNA);
            case "free":
                return communityRepository.findByCommunityCategoryOrderByBoardMainCreatedAtDesc(pageRequest, CommunityCategory.FREE);
            case "review":
                return communityRepository.findByCommunityCategoryOrderByBoardMainCreatedAtDesc(pageRequest, CommunityCategory.REVIEW);
            case "all":
                return communityRepository.findByOrderByBoardMainCreatedAtDesc(pageRequest);
            default:
                return null;
        }
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


        if (communityRequestDto.getCommunityImages()==null){
            community.updateTitle(communityRequestDto);
        }else {
            awsS3Service.removeCommunityImages(community.getId());
            List<CommunityImage> communityImages = communityRequestDto.getCommunityImages();
            community.update(communityRequestDto);
            imageUpdateToCommunity(communityImages, community);
        }

        Map<String, Object> data = new HashMap<>();
        data.put("communityId", community.getId());
        data.put("boardMainId", community.getBoardMain().getId());
        return data;
    }
}
