package com.finalproject.breeding.controller;

import com.finalproject.breeding.dto.CommunityRequestDto;
import com.finalproject.breeding.dto.CommunityResDto;
import com.finalproject.breeding.error.StatusResponseDto;
import com.finalproject.breeding.model.User;
import com.finalproject.breeding.model.board.Community;
import com.finalproject.breeding.repository.CommunityMapping;
import com.finalproject.breeding.service.CommunityService;
import com.finalproject.breeding.service.UserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class CommunityController {
//
//    private final CommunityService communityService;
//    private final UserService userService;
//
//    @PostMapping("/api/community") //커뮤니티글등록
//    public ResponseEntity<Object> communitySave(@RequestBody CommunityRequestDto communityRequestDto){
//        User user = userService.getUser();
//        Map<String, Object> data = communityService.communitySave(communityRequestDto, user);
//        return new ResponseEntity<>(new StatusResponseDto("등록 완료 되었습니다.", data), HttpStatus.OK);
//    }
//
//    @PatchMapping("/api/community/{communityId}")
//    public ResponseEntity<Object> communityUpdate(@PathVariable Long communityId,
//                                     @Validated(Update.class) @RequestBody CommunityRequestDto communityRequestDto){
//        User user = userService.getUser();
//        Map<String, Object> data = communityService.communityUpdate(communityId, communityRequestDto, user);
//        return new ResponseEntity<>(new StatusResponseDto("수정 완료 되었습니다.", data), HttpStatus.OK);
//    }
//
//    @DeleteMapping("/api/community/{communityId}")
//    public void communityDelete(@PathVariable Long communityId){
//        communityService.communityDelete(communityId);
//    }
//
//    @GetMapping("/api/community/{communityId}") //커뮤니티 특정 글 조회
//    public Community communityDetail(@PathVariable Long communityId){
//        return communityService.communityDetail(communityId);
//    }
//
//
//   @GetMapping("/api/community/category/{communityCategoryId}")
//   public Slice<Community> getCateCommunity(HttpServletRequest httpServletRequest,
//                                            @PathVariable Long communityCategoryId){
//       Long page = Long.parseLong(httpServletRequest.getParameter("page"));
//       return communityService.getCateCommunity(page, communityCategoryId);
//   }
//
//    @GetMapping("/api/community/heart/{communityCategoryId}")
//    public Slice<Community> getLikeCommunity(HttpServletRequest httpServletRequest,
//                                             @PathVariable Long communityCategoryId){
//        Long page = Long.parseLong(httpServletRequest.getParameter("page"));
//        return communityService.getLikeCommunity(page, communityCategoryId);
//    }
//
//    @GetMapping("/api/test12")
//    public Slice<CommunityMapping> get(HttpServletRequest httpServletRequest){
//        Long page = Long.parseLong(httpServletRequest.getParameter("page"));
//        return communityService.get(page);
//    }


}
