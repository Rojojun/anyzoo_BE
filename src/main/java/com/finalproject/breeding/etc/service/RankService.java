package com.finalproject.breeding.etc.service;

import com.finalproject.breeding.board.dto.PostResponseDto;
import com.finalproject.breeding.board.model.Post;
import com.finalproject.breeding.board.model.category.PostNReelsCategory;
import com.finalproject.breeding.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RankService {

    private final PostRepository postRepository;

    @Transactional
    public List<PostResponseDto> getWeekPost(String category) {

        LocalDate date = LocalDate.now().minusDays(7);
        System.out.println(date);

        PageRequest pageRequest = PageRequest.of(0, 3);
        switch (category) {
            case "cool":
                return postRepository.findByPostNReelsCategoryOrderByBoardMainLikeCntDesc(date, PostNReelsCategory.COOL, pageRequest);
            case "cute":
                return postRepository.findByPostNReelsCategoryOrderByBoardMainLikeCntDesc(date, PostNReelsCategory.CUTE, pageRequest);
            case "comic":
                return postRepository.findByPostNReelsCategoryOrderByBoardMainLikeCntDesc(date, PostNReelsCategory.COMIC, pageRequest);
            case "pretty":
                return postRepository.findByPostNReelsCategoryOrderByBoardMainLikeCntDesc(date, PostNReelsCategory.PRETTY, pageRequest);
            case "all":
                return postRepository.findByOrderByBoardMainLikeCntDesc(date, pageRequest);
            default:
                return null;
        }
    }


    public List<PostResponseDto> getDayPost(){

            LocalDate date = LocalDate.now().minusDays(1);
            System.out.println(date);

            PageRequest pageRequest = PageRequest.of(0, 3);
            return postRepository.findByOrderByBoardMainLikeCntDesc(date, pageRequest);
        }

}
