package com.finalproject.breeding.board.controller;

import com.finalproject.breeding.board.dto.*;
import com.finalproject.breeding.board.repository.TogetherRepository;
import com.finalproject.breeding.board.service.TogetherService;
import com.finalproject.breeding.etc.dto.response.StatusResponseDto;
import com.finalproject.breeding.board.repository.CityRepository;
import com.finalproject.breeding.board.repository.ProvinceRepository;
import com.finalproject.breeding.user.User;
import com.finalproject.breeding.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class TogetherController {

    private final TogetherService togetherService;
    private final UserService userService;
    private final CityRepository cityRepository;
    private final ProvinceRepository provinceRepository;
    private final TogetherRepository togetherRepository;

    @GetMapping("/api/together/city")
    public List<CityResponseDto> cityAreas(){
        return cityRepository.findAllByOrderByIdAsc();
    }

    @GetMapping("/api/together/province/{cityId}")
    public List<ProvinceResponseDto> provinceAreas(@PathVariable Long cityId){
        return provinceRepository.findAllByCityAreasId(cityId);
    }


    @PostMapping("/api/together")
    public ResponseEntity<Object> registTogether(@RequestBody TogetherRequestDto togetherRequestDto){
        User user = userService.getUser();
        Map<String, Object> data = togetherService.registTogether(togetherRequestDto, user);
        return new ResponseEntity<>(new StatusResponseDto("등록 되었습니다.", data), HttpStatus.OK);
    }

    @GetMapping("/api/together/detail/{boardMainId}") // 특정 글 조회
    public TogetherResponseDto getTogether(@PathVariable Long boardMainId){
        return togetherRepository.findTogetherByBoardMainId(boardMainId);
    }

    @GetMapping("/api/together/category/{provinceId}") // 지역조회
    public Slice<TogetherResponseDto> getProvinceTogether(HttpServletRequest httpServletRequest, @PathVariable Long provinceId){
        int page = Integer.parseInt(httpServletRequest.getParameter("page"));
        return togetherService.getProvinceTogether(page, provinceId);
    }
    @GetMapping("/api/together") // 전체조회
    public Slice<TogetherResponseDto> getProvinceTogether(HttpServletRequest httpServletRequest){
        int page = Integer.parseInt(httpServletRequest.getParameter("page"));
        return togetherService.getAllTogether(page);
    }

    @PatchMapping("/api/together/detail/{boardMainId}")
    public ResponseEntity<Object> updateTogether(@RequestBody TogetherRequestDto togetherRequestDto, @PathVariable Long boardMainId){
        User user = userService.getUser();
        Map<String, Object> data = togetherService.updateTogether(togetherRequestDto, boardMainId, user);
        return new ResponseEntity<>(new StatusResponseDto("수정 되었습니다.", data), HttpStatus.OK);
    }

    @DeleteMapping("/api/together/detail/{boardMainId}")
    public ResponseEntity<Object> deleteTogether(@PathVariable Long boardMainId){
        User user = userService.getUser();
        togetherService.deleteTogether(boardMainId, user);
        return new ResponseEntity<>(new StatusResponseDto("삭제 되었습니다.", ""), HttpStatus.OK);
    }
}
