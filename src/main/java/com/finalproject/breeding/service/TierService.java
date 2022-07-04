package com.finalproject.breeding.service;

import com.finalproject.breeding.model.User;
import com.finalproject.breeding.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TierService {

    private final UserRepository userRepository;

    @Transactional
    public void upTenExp(Long userId){
        User user = userRepository.findById(userId).orElseThrow(
                ()->new NullPointerException("해당 유저가 존재하지 않습니다.")
        );
        long exp = user.getExp()+10L;
        checkExp(user, exp);
        userRepository.save(user);
    }

    @Transactional
    public void upFiveExp(Long userId){
        User user = userRepository.findById(userId).orElseThrow(
                ()->new NullPointerException("해당 유저가 존재하지 않습니다.")
        );
        long exp = user.getExp()+5L;
        checkExp(user, exp);
        userRepository.save(user);
    }

    public void checkExp(User user, Long exp){
        if (user.getTier()<=2 || user.getExp()>=10000){
            int lvUp = user.getTier()+1;
            user.setTier(lvUp);
            user.setExp(exp-10000);
        } else if (user.getTier()<=4 || user.getExp()>=20000) {
            int lvUp = user.getTier()+1;
            user.setTier(lvUp);
            user.setExp(exp-20000);
        } else if(user.getTier()==5 || user.getExp()>=40000){
            user.setExp(40000L);
        } else {
            user.setExp(exp);
        }
    }
}
