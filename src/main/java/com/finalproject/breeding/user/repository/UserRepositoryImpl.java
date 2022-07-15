package com.finalproject.breeding.user.repository;

import com.finalproject.breeding.user.QUser;
import com.finalproject.breeding.user.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public UserRepositoryImpl(JPAQueryFactory queryFactory){
        this.queryFactory = queryFactory;
    }

    QUser user = QUser.user;

    @Override
    public List<User> getUserList(){
        return queryFactory
                .selectFrom(user)
                .fetch();
    }
}
