package com.finalproject.breeding.controller;

import com.finalproject.breeding.dto.CommunityRequestDto;
import com.finalproject.breeding.model.board.Community;
import com.finalproject.breeding.repository.CommunityRepository;
import com.finalproject.breeding.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CommunityController {
    private final CommunityService communityService;
    private final CommunityRepository communityRepository;

    //커뮤니티 작성
    @PostMapping("/api/community")
    public Community createCommunity(@RequestBody CommunityRequestDto requestDto,
                                     HttpServletRequest httpServletRequest
    ){
        Long boardMainId = 1L;
        Long communCategoryId = 1L;
        Long provinceAreasId = 1L;
        return communityService.createCommunity(requestDto, boardMainId, communCategoryId, provinceAreasId);
    }

    //커뮤니티 전체 조회
    @GetMapping("/api/community")
    public List<Community> getAllCommunity(){
        List<Community> communitys= communityRepository.findAll();
        return communitys;
    }

    //특정 커뮤니티 조회
    @GetMapping("/api/community/{communityId}")
    public Optional<Community> getCommunity(@PathVariable Long communityId){
        Optional<Community> community = communityRepository.findById(communityId);
        return community;
    }



    //커뮤니티 삭제
    @DeleteMapping("/api/community/edit/{communityId}")
    public Long deleteCommunity(@PathVariable Long communityId,
                            HttpServletRequest httpServletRequest){
        communityRepository.deleteById(communityId);
        return communityId;
    }

    //커뮤니티 수정
    @PatchMapping ("/api/community/edit/{communityId}")
    public Long patchCommunity(@RequestBody CommunityRequestDto requestDto,
                           @PathVariable Long communityId,
                           HttpServletRequest httpServletRequest) {

        communityService.patchCommunity(requestDto,communityId);
        return communityId;
    }
}
