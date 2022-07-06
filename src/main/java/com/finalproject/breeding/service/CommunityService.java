package com.finalproject.breeding.service;

import com.finalproject.breeding.UserValidator;
import com.finalproject.breeding.dto.CommunityRequestDto;
import com.finalproject.breeding.dto.CommunityResDto;
import com.finalproject.breeding.error.CustomException;
import com.finalproject.breeding.error.ErrorCode;
import com.finalproject.breeding.model.User;
import com.finalproject.breeding.model.board.BoardMain;
import com.finalproject.breeding.model.board.Community;
import com.finalproject.breeding.model.category.CommunityCategory;
import com.finalproject.breeding.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final CommunityCategoryRepository communityCategoryRepository;
    private final BoardKindRepository boardKindRepository;
    private final BoardMainRepository boardMainRepository;

    @Transactional
    public Map<String, Object> communitySave(CommunityRequestDto communityRequestDto, User user) {

        String boardName = "community";

        Community community = Community.builder()
                .communityCategory(checkCategory(communityRequestDto.getName()))
                .boardMain(boardMainRepository.save(BoardMain.builder()
                        .boardKind(boardKindRepository.findByBoardName(boardName))
                        .likeCnt(0L)
                        .content(communityRequestDto.getContent())
                        .build()))
                .title(communityRequestDto.getTitle())
                .user(user)
                .build();
        communityRepository.save(community);
        Map<String, Object> data = new HashMap<>();
        data.put("communityId", community.getId());
        data.put("boardMainId", community.getBoardMain().getId());
        return data;
    }

    @Transactional //커뮤니티 글 수정
    public Map<String, Object> communityUpdate(Long communityId, CommunityRequestDto communityRequestDto, User user){
        Community community = communityRepository.findById(communityId).orElseThrow(()->new CustomException(ErrorCode.POST_NOT_FOUND));
        BoardMain boardMain = community.getBoardMain();
        UserValidator.validateBoardMainAndUser(user, community);

        boardMain.update(communityRequestDto);
        community.update(communityRequestDto, boardMain);

        Map<String, Object> data = new HashMap<>();
        data.put("communityId", community.getId());
        data.put("boardMainId", community.getBoardMain().getId());
        return data;
    }

    @Transactional // 커뮤니티 글 삭제(유저확인 필요-추가예정)
    public void communityDelete(Long communityId){
        Community community = communityRepository.findById(communityId).orElseThrow(()->new NullPointerException("d"));
        boardMainRepository.delete(community.getBoardMain());
        communityRepository.delete(community);
    }

    @Transactional(readOnly = true) //디테일글 조회
    public Community communityDetail(Long communityId) {
        Community community = communityRepository.findById(communityId).orElseThrow(()->new NullPointerException("d"));
        return community;
    }

    @Transactional(readOnly = true) // 커뮤니티 글 전체조회 및 카테고리별 조회(좋아요순, 무한스크롤)
    public Slice<Community> getCateCommunity(Long page, Long communityCategoryId) {
        PageRequest pageRequest = PageRequest.of(Math.toIntExact(page), 4, Sort.by(Sort.Direction.DESC, "boardMain.createdAt"));
        if (communityCategoryId==0L){
            return communityRepository.findAllByOrderByLikeCntDesc(pageRequest);
        }else{
        CommunityCategory communityCategory = communityCategoryRepository.findById(communityCategoryId).orElseThrow(()->new NullPointerException("해당 카테고리가 존재하지 않습니다."));
            return communityRepository.findByCommunityCategory(pageRequest, communityCategory);
        }
    }

    @Transactional(readOnly = true) // 커뮤니티 글 전체조회 및 카테고리별 조회(최신순, 무한스크롤)
    public Slice<Community> getLikeCommunity(Long page, Long communityCategoryId) {
        PageRequest pageRequest = PageRequest.of(Math.toIntExact(page), 4, Sort.by(Sort.Direction.DESC, "boardMain.createdAt"));
        if (communityCategoryId==0L){
            return communityRepository.findAll(pageRequest);
        }else{
            return communityRepository.findByCommunityCategoryIdOrderByCreatedAtDesc(pageRequest, communityCategoryId);
        }
    }

    public CommunityCategory checkCategory(String name){
        CommunityCategory communityCategory = communityCategoryRepository.findByCategoryName(name);
        if(communityCategory==null){throw new CustomException(ErrorCode.NOT_FOUND_BOARDKIND_INFO);}
        return communityCategory;
    }

    public Slice<CommunityMapping> get(Long page){
        PageRequest pageRequest = PageRequest.of(Math.toIntExact(page), 4, Sort.by(Sort.Direction.DESC, "boardMain.createdAt"));
        return communityRepository.findByOrderByIdDesc(pageRequest);
    }


}
