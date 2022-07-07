package com.finalproject.breeding.repository;

import com.finalproject.breeding.model.People;
import com.finalproject.breeding.model.User;
import com.finalproject.breeding.model.board.WithPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface PeopleRepository extends JpaRepository<People, Long> {
    People findByUserAndWithPost(User user, WithPost withPost);

    List<People> findAllByWithPost(WithPost withPost);
}
