package com.finalproject.breeding.etc.repository;

import com.finalproject.breeding.etc.model.People;
import com.finalproject.breeding.user.User;
import com.finalproject.breeding.board.model.WithPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PeopleRepository extends JpaRepository<People, Long> {
    People findByUserAndWithPost(User user, WithPost withPost);

    List<People> findAllByWithPost(WithPost withPost);
}
