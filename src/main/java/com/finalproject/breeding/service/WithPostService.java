package com.finalproject.breeding.service;

import com.finalproject.breeding.dto.WithPostRequestDto;
import com.finalproject.breeding.model.People;
import com.finalproject.breeding.model.User;
import com.finalproject.breeding.model.board.BoardMain;
import com.finalproject.breeding.model.board.WithPost;
import com.finalproject.breeding.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WithPostService {

    private final WithPostRepository withPostRepository;
    private final WithPostCategoryRepository withPostCategoryRepository;
    private final BoardMainRepository boardMainRepository;
    private final BoardKindRepository boardKindRepository;
    private final UserRepository userRepository;
    private final PeopleRepository peopleRepository;

    @Transactional
    public void withPostSave(WithPostRequestDto withPostRequestDto) {
        User user = userRepository.findById(1L).orElseThrow(()->new NullPointerException("해당유저가 존재하지 않습니다."));
        WithPost withPost = new WithPost(
                withPostCategoryRepository.findByName(withPostRequestDto.getName()),
                withPostRequestDto,
                boardMainRepository.save(
                        new BoardMain(
                                boardKindRepository.findById(1L).orElseThrow(()->new NullPointerException("해당 카테고리가 존재하지 않습니다.")),
                                withPostRequestDto,
                                user
                        )
                )
        );
        withPostRepository.save(withPost);
        People people = new People(user, withPost);
        peopleRepository.save(people);
    }
}
