package com.finalproject.breeding.etc.service;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.finalproject.breeding.board.dto.PostResponseDto;
import com.finalproject.breeding.board.model.Post;
import com.finalproject.breeding.board.model.category.PostNReelsCategory;
import com.finalproject.breeding.board.repository.PostRepository;
import com.finalproject.breeding.etc.model.Timestamped;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RankService {

    private final PostRepository postRepository;

    @Transactional
    public List<PostResponseDto> getWeekPost(String category) {

        LocalDate week = LocalDate.now().minusDays(6);
        LocalTime time = LocalTime.of(0, 0);

        LocalDateTime date = LocalDateTime.of(week, time);

        System.out.println(date);

        PageRequest pageRequest = PageRequest.of(0, 3);
        switch (category) {
            case "cool":
                return postRepository.findPostByPostNReelsCategoryOrderByBoardMainLikeCntDesc(date, PostNReelsCategory.COOL, pageRequest);
            case "cute":
                return postRepository.findPostByPostNReelsCategoryOrderByBoardMainLikeCntDesc(date, PostNReelsCategory.CUTE, pageRequest);
            case "comic":
                return postRepository.findPostByPostNReelsCategoryOrderByBoardMainLikeCntDesc(date, PostNReelsCategory.COMIC, pageRequest);
            case "pretty":
                return postRepository.findPostByPostNReelsCategoryOrderByBoardMainLikeCntDesc(date, PostNReelsCategory.PRETTY, pageRequest);
            case "all":
                return postRepository.findPostByOrderByBoardMainLikeCntDesc(date, pageRequest);
            default:
                return null;
        }
    }


    public List<PostResponseDto> getDayPost(){

        LocalDate week = LocalDate.now();
        LocalTime time = LocalTime.of(0, 0);

        LocalDateTime date = LocalDateTime.of(week, time);

            PageRequest pageRequest = PageRequest.of(0, 2);
            return postRepository.findPostByOrderByBoardMainLikeCntDesc(date, pageRequest);
        }

}
