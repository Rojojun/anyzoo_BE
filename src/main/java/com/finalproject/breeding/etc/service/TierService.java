package com.finalproject.breeding.etc.service;

import com.finalproject.breeding.user.User;
import com.finalproject.breeding.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TierService {

    private final UserRepository userRepository;

    @Transactional
    public void upTenExp(User user){
        user.tenExpUp(user);
        checkExp(user);
        userRepository.save(user);
    }

    @Transactional
    public void upFiveExp(User user){
        user.fiveExpUp(user);
        checkExp(user);
        userRepository.save(user);
    }

    public void checkExp(User user){
        if (user.getTier()<=2 && user.getExp()>=10000){
            user.oneLvUp(user);
        } else if (user.getTier()<=4 && user.getExp()>=20000) {
            user.twoLvUp(user);
        } else if(user.getTier()==5 && user.getExp()>=40000) {
            user.threeLvUp(user);
        }
    }
}
