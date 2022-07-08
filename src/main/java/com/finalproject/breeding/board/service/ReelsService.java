package com.finalproject.breeding.board.service;

import com.finalproject.breeding.board.dto.ReelsRequestDto;
import com.finalproject.breeding.board.model.BoardMain;
import com.finalproject.breeding.board.model.Reels;
import com.finalproject.breeding.board.repository.BoardMainRepository;
import com.finalproject.breeding.board.repository.ReelsRepository;
import com.finalproject.breeding.user.User;
import com.finalproject.breeding.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ReelsService {
    private final ReelsRepository reelsRepository;
    private final BoardMainRepository boardMainRepository;
    private final UserRepository userRepository;
    public Map<String, Object> registReels(ReelsRequestDto reelsRequestDto,
                                           User username) {
        User user = userRepository.findByUsername(username.getUsername()).orElseThrow(
                () -> new RuntimeException("아이디가 존재하지 않습니다.")
        );

        BoardMain boardMain = new BoardMain(reelsRequestDto);
        boardMainRepository.save(boardMain);

        Reels reels = new Reels(reelsRequestDto, boardMain, user);
        reelsRepository.save(reels);

        Map<String,Object> data = new HashMap<>();
        data.put("reelsId", reels.getId());
        data.put("boardMainId", reels.getBoardMain().getId());
        return data;
    }
}
