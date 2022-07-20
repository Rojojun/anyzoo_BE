package com.finalproject.breeding.etc.service;

import com.finalproject.breeding.board.model.BoardMain;
import com.finalproject.breeding.board.repository.BoardMainRepository;
import com.finalproject.breeding.dto.CommentResponseDto;
import com.finalproject.breeding.error.ErrorCode;
import com.finalproject.breeding.etc.dto.*;
import com.finalproject.breeding.etc.model.Comment;
import com.finalproject.breeding.etc.model.Reply;
import com.finalproject.breeding.etc.repository.CommentRepository;
import com.finalproject.breeding.etc.repository.ReplyRepository;
import com.finalproject.breeding.user.User;
import com.finalproject.breeding.user.repository.UserRepository;
import com.finalproject.breeding.user.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final CommentRepository commentRepository;
    private final ReplyRepository replyRepository;
    private final UserRepository userRepository;

    //댓글 작성
    @Transactional
    public ResponseEntity<MyDto> createReply(ReplyRequestDto requestDto, Long commentId, String username) {

        MyDto dto = new MyDto();
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        Optional<Comment> comment1 = commentRepository.findById(commentId);
        Optional<User> user1 = userRepository.findByUsername(username);
        dto.setStatus(ErrorCode.OK);
        if (!comment1.isPresent()) {
            dto.setData(null);
            dto.setMessage("댓글이 없습니다!");
        } else if (!user1.isPresent()) {
            dto.setData(null);
            dto.setMessage("유저가 없습니다!");
        } else {
            BoardMain boardMain = comment1.get().getBoardMain();
            Comment comment = comment1.get();
            User user = user1.get();
            Reply reply = new Reply(requestDto, comment, user);

            Long replyId = replyRepository.save(reply).getId();
            boardMain.plusCommentCnt(boardMain);

            dto.setData(replyId);
            dto.setMessage("대댓글 등록!");
        }
        return new ResponseEntity<>(dto, header, HttpStatus.OK);
    }


    //댓글 삭제
    @Transactional
    public ResponseEntity<MyDto> deleteReply(Long replyId) {

        MyDto dto = new MyDto();
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        dto.setStatus(ErrorCode.OK);
        dto.setData(replyId);

        String userId = SecurityUtil.getCurrentUsername();
        Optional<Reply> reply =replyRepository.findById(replyId);

        if(!reply.isPresent()){
            dto.setMessage("대댓글이 없습니다!");
        } else if (!Objects.equals(userId, reply.get().getUser().getUsername())) {
            dto.setMessage("사용자의 댓글이 아닙니다!");
        }else{
            BoardMain boardMain = reply.get().getComment().getBoardMain();
            boardMain.minusCommentCnt(boardMain);
            replyRepository.deleteById(replyId);
            dto.setMessage("대댓글 삭제!");
        }
        return new ResponseEntity<>(dto, header, HttpStatus.OK);
    }

    //댓글 수정
    @Transactional
    public ResponseEntity<MyDto> patchReply(ReplyRequestDto requestDto, Long replyId) {

        MyDto dto = new MyDto();
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        dto.setStatus(ErrorCode.OK);
        dto.setData(replyId);
        String userId = SecurityUtil.getCurrentUsername(); //현제 로그인한 유저 pk
        Optional<Reply> reply1 =replyRepository.findById(replyId);

        if(!reply1.isPresent()){
            dto.setMessage("대댓글이 없습니다!");
        }else if (!Objects.equals(userId, reply1.get().getUser().getUsername())) {   //댓글의 닉네임와 일치한다면
            dto.setMessage("사용자의 댓글이 아닙니다!");
        }else{
            Reply reply = reply1.get();
            reply.updateReply(requestDto);
            dto.setMessage("댓글 수정!");
        }
        return new ResponseEntity<>(dto, header, HttpStatus.OK);

    }


    //대댓글 모두 불러오기
    @Transactional
    public List<ReplyMapping> getAllReply(Long commentId) {
        List<ReplyMapping> replyies = replyRepository.findAllByCommentId(commentId);
        return replyies;
    }
}
