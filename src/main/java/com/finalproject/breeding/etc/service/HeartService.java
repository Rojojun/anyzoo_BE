package com.finalproject.breeding.etc.service;

import com.finalproject.breeding.error.CustomException;
import com.finalproject.breeding.error.ErrorCode;
import com.finalproject.breeding.etc.model.Heart;
import com.finalproject.breeding.user.User;
import com.finalproject.breeding.board.model.BoardMain;
import com.finalproject.breeding.board.repository.BoardMainRepository;
import com.finalproject.breeding.etc.repository.HeartMapping;
import com.finalproject.breeding.etc.repository.HeartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class HeartService {

    private final BoardMainRepository boardMainRepository;
    private final HeartRepository heartRepository;

    @Transactional
    public Map<String, Object> upHeart(Long boardMainId, User user) {
        BoardMain boardMain = boardMainRepository.findById(boardMainId).orElseThrow(
                ()->new CustomException(ErrorCode.POST_NOT_FOUND)
        );
        Map<String, Object> data = new HashMap<>();
        if (heartRepository.findByUserAndBoardMain(user, boardMain)==null){
            Heart heart = new Heart(user, boardMain);
            heartRepository.save(heart);
            boardMain.plusLikeCnt(boardMain);
            data.put("like", false);

        } else {
            Heart heart = heartRepository.getHeartByUserAndBoardMain(user, boardMain);
            heartRepository.delete(heart);
            boardMain.minusLikeCnt(boardMain);
            data.put("like", true);
        }
        return data;

    }

    public boolean checkHeart(Long boardMainId, User user) {

        BoardMain boardMain = boardMainRepository.findById(boardMainId).orElseThrow(
                ()->new CustomException(ErrorCode.POST_NOT_FOUND)
        );
        return heartRepository.findByUserAndBoardMain(user, boardMain) == null;
    }

    @Transactional
    public List<HeartMapping> getHeart(User user){
        return heartRepository.findByUser(user);
    }
}
