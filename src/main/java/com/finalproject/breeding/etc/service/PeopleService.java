package com.finalproject.breeding.etc.service;

import com.finalproject.breeding.etc.model.People;
import com.finalproject.breeding.user.User;
import com.finalproject.breeding.board.model.WithPost;
import com.finalproject.breeding.etc.repository.PeopleRepository;
import com.finalproject.breeding.user.repository.UserRepository;
import com.finalproject.breeding.board.repository.WithPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PeopleService {

    private final PeopleRepository peopleRepository;
    private final UserRepository userRepository;
    private final WithPostRepository withPostRepository;


    public void joinPeople(Long withpostId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                ()->new NullPointerException("해당 유저가 존재하지 않습니다.")
        );
        WithPost withPost = withPostRepository.findById(withpostId).orElseThrow(
                ()->new NullPointerException("해당 게시글이 존재하지 않습니다.")
        );

        if (peopleRepository.findByUserAndWithPost(user, withPost)==null){
            People people = new People(user, withPost);
            peopleRepository.save(people);
        } else {
            People people = peopleRepository.findByUserAndWithPost(user, withPost);
            peopleRepository.delete(people);
        }

        withPost.setPeopleCnt((long) peopleRepository.findAllByWithPost(withPost).size());
        withPostRepository.save(withPost);
    }
}
