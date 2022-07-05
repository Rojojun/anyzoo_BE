package com.finalproject.breeding.service;

import com.finalproject.breeding.dto.CommunityRequestDto;
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

@Service
@RequiredArgsConstructor
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final CommunityCategoryRepository communityCategoryRepository;
    private final UserRepository userRepository;
    private final BoardKindRepository boardKindRepository;
    private final BoardMainRepository boardMainRepository;

    @Transactional
    public void communitySave(CommunityRequestDto communityRequestDto, String username) {
        CommunityCategory communityCategory = new CommunityCategory("test");
        communityCategoryRepository.save(communityCategory);
        User user = userRepository.findByUsername(username).orElseThrow(()->new NullPointerException("해당 유저가 존재하지 않습니다."));
        communityRepository.save(
                new Community(
                        communityCategoryRepository.findByName(communityRequestDto.getName()),
                        communityRequestDto,
                        boardMainRepository.save(
                                new BoardMain(
                                        boardKindRepository.findById(1L).orElseThrow(()->new NullPointerException("해당 카테고리가 존재하지 않습니다.")),
                                        communityRequestDto,
                                        user
                                )
                        )
                )
        );
    }

    @Transactional //커뮤니티 글 수정
    public void communityUpdate(Long communityId, CommunityRequestDto communityRequestDto){

        Community community = communityDetail(communityId);
        BoardMain boardMain = community.getBoardMain();
        boardMain.update(communityRequestDto);
        community.updaate(communityRequestDto, boardMain);
    }

    @Transactional // 커뮤니티 글 삭제(유저확인 필요-추가예정)
    public void communityDelete(Long communityId){
        Community community = communityDetail(communityId);
        boardMainRepository.delete(community.getBoardMain());
        communityRepository.delete(community);
    }

    @Transactional(readOnly = true) //디테일글 조회
    public Community communityDetail(Long communityId) {
        return communityRepository.findById(communityId).orElseThrow(()->new NullPointerException("해당 글을 찾지 못했습니다."));
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

}
