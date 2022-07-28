package com.finalproject.breeding.board.service;

import com.finalproject.breeding.board.dto.ReelsRequest4EditDto;
import com.finalproject.breeding.board.dto.ReelsRequestDto;
import com.finalproject.breeding.board.dto.ReelsResponseDto;
import com.finalproject.breeding.board.model.BoardMain;
import com.finalproject.breeding.board.model.Reels;
import com.finalproject.breeding.board.model.category.PostNReelsCategory;
import com.finalproject.breeding.board.repository.BoardMainRepository;
import com.finalproject.breeding.board.repository.ReelsRepository;
import com.finalproject.breeding.error.CustomException;
import com.finalproject.breeding.error.ErrorCode;
import com.finalproject.breeding.user.User;
import com.finalproject.breeding.user.repository.UserRepository;
import com.finalproject.breeding.video.util.S3VideoUploader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class ReelsService {
    private final ReelsRepository reelsRepository;
    private final BoardMainRepository boardMainRepository;
    private final UserRepository userRepository;
    private final S3VideoUploader s3VideoUploader;
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

    @Transactional
    public ReelsResponseDto getReelsDetail(Long boardMainId){
        Reels reels = reelsRepository.findByBoardMainId(boardMainId);
        return new ReelsResponseDto(reels);
    }

    @Transactional
    public Slice<ReelsResponseDto> readCategoryReels(int page, String category) {
        PageRequest pageRequest = PageRequest.of(Math.toIntExact(page), 1, Sort.by(Sort.Direction.DESC, "boardMain.createdAt"));
        switch (category) {
            case "cool":
                return reelsRepository.findReelsByPostNReelsCategory(pageRequest, PostNReelsCategory.COOL);
            case "coollike":
                return reelsRepository.findReelsByPostNReelsCategoryOrderByBoardMainLikeCntDesc(pageRequest, PostNReelsCategory.COOL);
            case "cute":
                return reelsRepository.findReelsByPostNReelsCategory(pageRequest, PostNReelsCategory.CUTE);
            case "cutelike":
                return reelsRepository.findReelsByPostNReelsCategoryOrderByBoardMainLikeCntDesc(pageRequest, PostNReelsCategory.CUTE);
            case "comic":
                return reelsRepository.findReelsByPostNReelsCategory(pageRequest, PostNReelsCategory.COMIC);
            case "comiclike":
                return reelsRepository.findReelsByPostNReelsCategoryOrderByBoardMainLikeCntDesc(pageRequest, PostNReelsCategory.COMIC);
            case "pretty":
                return reelsRepository.findReelsByPostNReelsCategory(pageRequest, PostNReelsCategory.PRETTY);
            case "prettylike":
                return reelsRepository.findReelsByPostNReelsCategoryOrderByBoardMainLikeCntDesc(pageRequest, PostNReelsCategory.PRETTY);
            case "all":
                return reelsRepository.findReelsByOrderByIdDesc(pageRequest);
            case "alllike":
                return reelsRepository.findReelsByOrderByBoardMainLikeCntDesc(pageRequest);
            default:
                return null;
        }
    }

    public void deleteReels(Long boardMainId, User user) throws UnsupportedEncodingException {
        Reels reels = reelsRepository.findByBoardMainId(boardMainId);

        if (!Objects.equals(user.getId(), reels.getUser().getId())) {
            throw new CustomException(ErrorCode.POST_DELETE_WRONG_ACCESS);
        }
        s3VideoUploader.remove(reels.getId());
        reelsRepository.delete(reels);
    }

    // 수정 부분 동영상, 썸네일 포함 수정할지 논의 중
    @Transactional
    public Map<String, Object> updateReels(Long boardMainId, ReelsRequest4EditDto requestDto, User user) {
        Reels reels = reelsRepository.findByBoardMainId(boardMainId);
        if (!Objects.equals(user.getId(), reels.getUser().getId())) {
            throw new CustomException(ErrorCode.POST_UPDATE_WRONG_ACCESS);
        }

        reels.getBoardMain().updateReels(requestDto);
        reels.updateReels(requestDto, reels.getBoardMain());

        Map<String, Object> data =new HashMap<>();
        data.put("reelsId", reels.getId());
        data.put("boardMainId", reels.getBoardMain().getId());
        return data;
    }

    // 릴스 동영상 및 썸네일 업데이트 관련 메소드 -> 현 상태로 미구현 예정
/*    public void mediaUpdateToReels(String video, String thumbnail, Reels reels) {
        Reels saveReels = reels;
        video = saveReels.getVideo();
        thumbnail = saveReels.getTitleImg();
    }*/
}
