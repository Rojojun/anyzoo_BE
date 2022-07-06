package com.finalproject.breeding.service;

import com.finalproject.breeding.model.board.Post;
import com.finalproject.breeding.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RankService {

    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public List<Post> getWeekPost() {

        Calendar time = new GregorianCalendar();
        time.add(Calendar.DATE, -7);
        System.out.println(time);

        return postRepository.findByOrderByLikeCntDesc(PageRequest.of(0, 3), time);
    }


    @Transactional(readOnly = true)
    public List<Post> getNowPost() {
        Calendar time = new GregorianCalendar();
        time.add(Calendar.DATE, -1);

        return postRepository.findByOrderByLikeCntDesc(PageRequest.of(0, 2), time);
    }
}
