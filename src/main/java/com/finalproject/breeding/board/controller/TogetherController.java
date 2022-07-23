package com.finalproject.breeding.board.controller;

import com.finalproject.breeding.board.dto.CityResponseDto;
import com.finalproject.breeding.board.dto.ProvinceResponseDto;
import com.finalproject.breeding.board.dto.TogetherRequestDto;
import com.finalproject.breeding.board.model.Together;
import com.finalproject.breeding.board.service.TogetherService;
import com.finalproject.breeding.etc.dto.StatusResponseDto;
import com.finalproject.breeding.board.repository.CityRepository;
import com.finalproject.breeding.board.repository.ProvinceRepository;
import com.finalproject.breeding.user.User;
import com.finalproject.breeding.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class TogetherController {

    private final TogetherService togetherService;
    private final UserService userService;
    private final CityRepository cityRepository;
    private final ProvinceRepository provinceRepository;

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

//    @GetMapping("/api/together/detail/{boardMainId}")
//    public Together getTogether(@PathVariable Long boardMainId){
//
//    }



    @PostMapping("/api/together/join/{boardMainId}")
    public ResponseEntity<Object> joinTogether(@PathVariable Long boardMainId){
        User user = userService.getUser();
        Map<String, Object> data = togetherService.joinTogether(boardMainId, user);
        return new ResponseEntity<>(new StatusResponseDto("참여 했습니다.",data), HttpStatus.OK);
    }

}
