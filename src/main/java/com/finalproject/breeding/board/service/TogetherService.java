package com.finalproject.breeding.board.service;

import com.finalproject.breeding.board.dto.TogetherRequestDto;
import com.finalproject.breeding.board.dto.TogetherResponseDto;
import com.finalproject.breeding.board.model.BoardMain;
import com.finalproject.breeding.board.model.Community;
import com.finalproject.breeding.board.model.Post;
import com.finalproject.breeding.board.model.Together;
import com.finalproject.breeding.board.model.category.ProvinceAreas;
import com.finalproject.breeding.board.repository.BoardMainRepository;
import com.finalproject.breeding.board.repository.ProvinceRepository;
import com.finalproject.breeding.board.repository.TogetherRepository;
import com.finalproject.breeding.error.CustomException;
import com.finalproject.breeding.error.ErrorCode;
import com.finalproject.breeding.image.AwsS3Service;
import com.finalproject.breeding.image.model.PostImage;
import com.finalproject.breeding.image.model.TogetherImage;
import com.finalproject.breeding.user.User;
import com.finalproject.breeding.user.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class TogetherService {

    private final BoardMainRepository boardMainRepository;
    private final TogetherRepository togetherRepository;
    private final ProvinceRepository provinceRepository;
    private final AwsS3Service awsS3Service;

    @Transactional
    public Map<String, Object> registTogether(TogetherRequestDto togetherRequestDto, User user) {
        ProvinceAreas provinceAreas = provinceRepository.findById(togetherRequestDto.getProvinceId()).orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND_PROVINCE));
        Together together;
        if(togetherRequestDto.getTogetherImages()!=null){
            List<TogetherImage> togetherImages = togetherRequestDto.getTogetherImages();
            together = Together.builder()
                    .provinceAreas(provinceAreas)
                    .user(user)
                    .boardMain(boardMainRepository.save(new BoardMain(togetherRequestDto)))
                    .title(togetherRequestDto.getTitle())
                    .limitPeople(togetherRequestDto.getLimitPeople())
                    .peopleCnt(1)
                    .dday(togetherRequestDto.getDday())
                    .togetherImages(togetherImages)
                    .build();

            imageUpdateToTogether(togetherImages, togetherRepository.save(together));

        }else {
            together = Together.builder()
                    .provinceAreas(provinceAreas)
                    .user(user)
                    .boardMain(boardMainRepository.save(new BoardMain(togetherRequestDto)))
                    .title(togetherRequestDto.getTitle())
                    .limitPeople(togetherRequestDto.getLimitPeople())
                    .peopleCnt(1)
                    .dday(togetherRequestDto.getDday())
                    .build();
            togetherRepository.save(together);
        }


        Map<String, Object> data = new HashMap<>();
        data.put("togetherId", together.getId());
        data.put("boardMainId", together.getBoardMain().getId());
        return data;
    }

    private void imageUpdateToTogether(List<TogetherImage> togetherImages, Together together) {
        for (TogetherImage togetherImage : togetherImages){
            togetherImage.updateToTogether(together);
        }
    }


    @Transactional(readOnly = true)
    public Slice<TogetherResponseDto> getProvinceTogether(int page, Long provinceId) {
        PageRequest pageRequest = PageRequest.of(page, 5);
        return togetherRepository.findByProvinceIdOrderByBoardMainCreatedAtDesc(pageRequest, provinceId);
    }

    @Transactional(readOnly = true)
    public Slice<TogetherResponseDto> getAllTogether(int page) {
        PageRequest pageRequest = PageRequest.of(page, 5);
        return togetherRepository.findByOrderByBoardMainCreatedAtDesc(pageRequest);
    }

    public Map<String, Object> updateTogether(TogetherRequestDto togetherRequestDto, Long boardMainId, User user) {
        Together together = togetherRepository.findByBoardMainId(boardMainId);
        UserValidator.validateUpdate4User(user, together.getUser().getId());
        together.getBoardMain().updateTogether(togetherRequestDto);
        together.updateTogether(togetherRequestDto);
        togetherRepository.save(together);

        Map<String, Object> data = new HashMap<>();
        data.put("communityId", together.getId());
        data.put("boardMainId", together.getBoardMain().getId());
        return data;
    }

    public void deleteTogether(Long boardMainId, User user) {
        Together together = togetherRepository.findByBoardMainId(boardMainId);
        UserValidator.validateDelete4User(user, together.getUser().getId());
        awsS3Service.removeTogetherImages(together.getId());
        togetherRepository.delete(together);
    }
}
