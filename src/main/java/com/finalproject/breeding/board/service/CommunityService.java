package com.finalproject.breeding.board.service;

import com.finalproject.breeding.board.dto.CommunityRequestDto;
import com.finalproject.breeding.board.dto.CommunityResponseDto;
import com.finalproject.breeding.board.model.BoardMain;
import com.finalproject.breeding.board.model.Community;
import com.finalproject.breeding.board.model.category.CommunityCategory;
import com.finalproject.breeding.board.model.category.PostNReelsCategory;
import com.finalproject.breeding.board.repository.BoardMainRepository;
import com.finalproject.breeding.board.repository.CommunityRepository;
import com.finalproject.breeding.error.CustomException;
import com.finalproject.breeding.error.ErrorCode;
import com.finalproject.breeding.image.ImageRequestDto;
import com.finalproject.breeding.image.model.CommunityImage;
import com.finalproject.breeding.image.model.PostImage;
import com.finalproject.breeding.image.repository.CommunityImageRepository;
import com.finalproject.breeding.user.User;
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
    private final CommunityImageRepository communityImageRepository;


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


    public Slice<CommunityResponseDto> readCommunity(Long page, String category) {
        PageRequest pageRequest = PageRequest.of(Math.toIntExact(page), 5);
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
        if (!Objects.equals(user.getId(), community.getUser().getId())) {
            throw new CustomException(ErrorCode.POST_DELETE_WRONG_ACCESS);
        }
        //사진삭제 추가해야함!
        communityRepository.delete(community);
    }

    public Map<String, Object> updateCommunity(Long boardMainId, CommunityRequestDto communityRequestDto, User user) {
        Community community = communityRepository.findCommunityByBoardMainId(boardMainId);
        if (!Objects.equals(user.getId(), community.getUser().getId())) {
            throw new CustomException(ErrorCode.POST_DELETE_WRONG_ACCESS);
        }
        community.getBoardMain().update(communityRequestDto);
        community.update(communityRequestDto, community.getBoardMain());

        Map<String, Object> data = new HashMap<>();
        data.put("communityId", community.getId());
        data.put("boardMainId", community.getBoardMain().getId());
        return data;
    }
}
