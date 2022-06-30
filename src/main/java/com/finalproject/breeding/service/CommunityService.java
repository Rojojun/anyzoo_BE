package com.finalproject.breeding.service;

import com.finalproject.breeding.dto.CommunityRequestDto;
import com.finalproject.breeding.dto.ReelsRequestDto;
import com.finalproject.breeding.model.board.BoardMain;
import com.finalproject.breeding.model.board.Community;
import com.finalproject.breeding.model.board.Reels;
import com.finalproject.breeding.model.category.CommunityCategory;
import com.finalproject.breeding.model.category.PostCategory;
import com.finalproject.breeding.model.category.ProvinceAreas;
import com.finalproject.breeding.repository.BoardMainRepository;
import com.finalproject.breeding.repository.CommunityCategoryRepository;
import com.finalproject.breeding.repository.CommunityRepository;
import com.finalproject.breeding.repository.ProvinceAreasRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommunityService {

    private final BoardMainRepository boardMainRepository;
    private final CommunityRepository communityRepository;
    private final CommunityCategoryRepository communityCategoryRepository;
    private final ProvinceAreasRepository provinceAreasRepository;


    //커뮤니티 작성
    @Transactional
    public Community createCommunity(CommunityRequestDto requestDto,
                                 Long boardMainId,
                                 Long communCategoryId,
                                 Long provinceAreasId) {

        BoardMain boardMain = boardMainRepository.findById(boardMainId).orElseThrow(
                ()-> new NullPointerException("게시글이 존재하지 않습니다."));

        CommunityCategory CommunityCategory = communityCategoryRepository.findById(communCategoryId).orElseThrow(
                ()-> new NullPointerException("커뮤니티 카테고리가 존재하지 않습니다."));

        ProvinceAreas provinceAreas  = provinceAreasRepository.findById(provinceAreasId).orElseThrow(
                ()-> new NullPointerException("지역이 존재하지 않습니다."));

        Community community = new Community(requestDto, boardMain, CommunityCategory, provinceAreas);
        communityRepository.save(community);
        return community;
    }

    //커뮤니티 수정
    @Transactional
    public void patchCommunity(CommunityRequestDto requestDto, Long communityId) {
        Community community = communityRepository.findById(communityId).orElseThrow(
                () -> new NullPointerException("커뮤니티가 존재하지 않습니다."));

        community.updateCommunity(requestDto);
    }
}
