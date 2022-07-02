package com.finalproject.breeding.controller;

import com.finalproject.breeding.dto.CommunityRequestDto;
import com.finalproject.breeding.model.User;
import com.finalproject.breeding.model.board.Community;
import com.finalproject.breeding.model.category.BoardKind;
import com.finalproject.breeding.model.category.CommunityCategory;
import com.finalproject.breeding.repository.BoardKindRepository;
import com.finalproject.breeding.repository.CommunityCategoryRepository;
import com.finalproject.breeding.repository.UserRepository;
import com.finalproject.breeding.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.data.domain.Slice;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class CommunityController {

    private final CommunityService communityService;
    private final CommunityCategoryRepository communityCategoryRepository;
    private final BoardKindRepository boardKindRepository;
    private final UserRepository userRepository;

    @PostMapping("/api/community") //커뮤니티글등록
    public void communitySave(@RequestBody CommunityRequestDto communityRequestDto){
        communityService.communitySave(communityRequestDto);
    }

    @PatchMapping("/api/community/{communityId}")
    public void communityUpdate(@PathVariable Long communityId,
                                     @Validated(Update.class) @RequestBody CommunityRequestDto communityRequestDto){
        communityService.communityUpdate(communityId, communityRequestDto);
    }

    @DeleteMapping("/api/community/{communityId}")
    public void communityDelete(@PathVariable Long communityId){
        communityService.communityDelete(communityId);
    }

    @GetMapping("/api/community/{communityId}") //커뮤니티 특정 글 조회
    public Community communityDetail(@PathVariable Long communityId){
        return communityService.communityDetail(communityId);
    }


   @GetMapping("/api/community/category/{communityCategoryId}")
   public Slice<Community> getCateCommunity(HttpServletRequest httpServletRequest,
                                            @PathVariable Long communityCategoryId){
       Long page = Long.parseLong(httpServletRequest.getParameter("page"));
       return communityService.getCateCommunity(page, communityCategoryId);
   }

    @GetMapping("/api/community/heart/{communityCategoryId}")
    public Slice<Community> getLikeCommunity(HttpServletRequest httpServletRequest,
                                             @PathVariable Long communityCategoryId){
        Long page = Long.parseLong(httpServletRequest.getParameter("page"));
        return communityService.getLikeCommunity(page, communityCategoryId);
    }


    @PostMapping("/api/test")
    public void test(){
        communityCategoryRepository.save(new CommunityCategory("test"));
        boardKindRepository.save(new BoardKind("test"));
        userRepository.save(new User("test"));
    }
}
