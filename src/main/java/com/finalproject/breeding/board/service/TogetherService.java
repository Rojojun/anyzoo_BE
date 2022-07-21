package com.finalproject.breeding.board.service;

import com.finalproject.breeding.board.dto.TogetherRequestDto;
import com.finalproject.breeding.board.model.BoardMain;
import com.finalproject.breeding.board.model.Together;
import com.finalproject.breeding.board.repository.BoardMainRepository;
import com.finalproject.breeding.board.repository.TogetherRepository;
import com.finalproject.breeding.error.CustomException;
import com.finalproject.breeding.error.ErrorCode;
import com.finalproject.breeding.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class TogetherService {

    private final BoardMainRepository boardMainRepository;
    private final TogetherRepository togetherRepository;
    public Map<String, Object> registTogether(TogetherRequestDto togetherRequestDto, User user) {



        Together together = new Together(
                togetherRequestDto,
                LocalDateTime.parse(togetherRequestDto.getDate()),
                boardMainRepository.save(
                        new BoardMain(togetherRequestDto)),
                user
                );
        togetherRepository.save(together);

        Map<String, Object> data = new HashMap<>();
        data.put("togetherId", together.getId());
        data.put("boardMainId", together.getBoardMain().getId());
        return data;
    }

    public Map<String, Object> joinTogether(Long boardMainId, User user) {
        Together together = togetherRepository.findByBoardMainId(boardMainId);
        if (together.isStatus()){

            together.joinTogether(together);
        }else {
            throw new CustomException(ErrorCode.ALREADY_PEOPLE_SET_FULL);
        }

        Map<String, Object> data = new HashMap<>();
        data.put("togetherId", together.getId());
        data.put("boardMainId", together.getBoardMain().getId());
        //data.put("roomId", )
        return data;
    }
}
