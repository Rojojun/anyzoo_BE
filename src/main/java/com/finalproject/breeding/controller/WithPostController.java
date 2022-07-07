package com.finalproject.breeding.controller;

import com.finalproject.breeding.dto.CommunityRequestDto;
import com.finalproject.breeding.dto.WithPostRequestDto;
import com.finalproject.breeding.repository.BoardKindRepository;
import com.finalproject.breeding.repository.CommunityCategoryRepository;
import com.finalproject.breeding.repository.UserRepository;
import com.finalproject.breeding.service.CommunityService;
import com.finalproject.breeding.service.PeopleService;
import com.finalproject.breeding.service.WithPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class WithPostController {

    private final WithPostService withPostService;
    private final PeopleService peopleService;
    private final CommunityCategoryRepository communityCategoryRepository;
    private final BoardKindRepository boardKindRepository;
    private final UserRepository userRepository;

    @PostMapping("/api/withpost") //커뮤니티글등록
    public void withPostSave(@RequestBody WithPostRequestDto withPostRequestDto){
        withPostService.withPostSave(withPostRequestDto);
    }

    @PostMapping("/api/withpost/join/{withpostId}")
    public void joinPeople(HttpServletRequest httpServletRequest,
                           @PathVariable Long withpostId){
        Long userId = 1L;
        peopleService.joinPeople(withpostId, userId);
    }
}
