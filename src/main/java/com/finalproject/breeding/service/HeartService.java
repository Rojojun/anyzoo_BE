package com.finalproject.breeding.service;

import com.finalproject.breeding.model.Heart;
import com.finalproject.breeding.model.User;
import com.finalproject.breeding.model.board.BoardMain;
import com.finalproject.breeding.repository.BoardMainRepository;
import com.finalproject.breeding.repository.HeartMapping;
import com.finalproject.breeding.repository.HeartRepository;
import com.finalproject.breeding.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class HeartService {

    private final BoardMainRepository boardMainRepository;
    private final HeartRepository heartRepository;
    private final UserRepository userRepository;

    @Transactional
    public void upHeart(Long boardMainId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                ()->new NullPointerException("해당 유저가 존재하지 않습니다.")
        )
        BoardMain boardMain = boardMainRepository.findById(boardMainId).orElseThrow(
                ()->new NullPointerException("해당 게시글이 존재하지 않습니다.")
        );

        if (heartRepository.findByUserAndBoardMain(user, boardMain)==null){
            Heart heart = new Heart(user, boardMain);
            heartRepository.save(heart);
        } else {
            Heart heart = heartRepository.getHeartByUserAndBoardMain(user, boardMain);
            heartRepository.delete(heart);
        }

        int count = heartRepository.findAllByBoardMain(boardMain).size();
        boardMain.setLikeCnt(count);
        boardMainRepository.save(boardMain);

    }

    public boolean checkHeart(Long boardMainId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                ()->new NullPointerException("해당 유저가 존재하지 않습니다.")
        );
        BoardMain boardMain = boardMainRepository.findById(boardMainId).orElseThrow(
                ()->new NullPointerException("해당 게시글이 존재하지 않습니다.")
        );
        return heartRepository.findByUserAndBoardMain(user, boardMain) == null;
    }

    @Transactional
    public List<HeartMapping> getHeart(Long userId){
        User user = userRepository.findById(userId).orElseThrow(
                ()->new NullPointerException("해당 유저가 존재하지 않습니다.")
        );
        return heartRepository.findByUser(user);
    }
}
