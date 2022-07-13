package com.finalproject.breeding.etc.service;


import com.finalproject.breeding.board.model.BoardMain;
import com.finalproject.breeding.board.repository.BoardMainRepository;
import com.finalproject.breeding.error.ErrorCode;

import com.finalproject.breeding.etc.dto.CommentRequestDto;
import com.finalproject.breeding.etc.dto.MyDto;
import com.finalproject.breeding.etc.dto.ReplyRequestDto;
import com.finalproject.breeding.etc.model.Comment;
import com.finalproject.breeding.etc.model.Reply;
import com.finalproject.breeding.etc.repository.CommentRepository;
import com.finalproject.breeding.etc.repository.ReplyRepository;
import com.finalproject.breeding.user.User;
import com.finalproject.breeding.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final UserRepository userRepository;
    private final ReplyRepository replyRepository;
    private final CommentRepository commentRepository;

    private final BoardMainRepository boardMainRepository;

    //대댓글 작성
    @Transactional
    public ResponseEntity<MyDto> createReply(ReplyRequestDto requestDto, Long boardMainId, Long CommentId, String username) {

        MyDto dto = new MyDto();
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        BoardMain boardMain = boardMainRepository.findById(boardMainId).orElseThrow(
                () -> new NullPointerException("게시글이 존재하지 않습니다."));

        Comment comment = commentRepository.findById(CommentId).orElseThrow(
                () -> new NullPointerException("댓글이 존재하지 않습니다."));

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new NullPointerException("유저가 존재하지 않습니다."));

        Reply reply = new Reply(requestDto, comment, user);

        boardMain.plusCommentCnt(boardMain);
        replyRepository.save(reply);


        dto.setStatus(ErrorCode.OK);
        dto.setData("CommentId :"+CommentId);
        dto.setMessage("대댓글 등록!");
        return new ResponseEntity<>(dto, header, HttpStatus.OK);
    }

    //대댓글 수정
    @Transactional
    public void patchReply(ReplyRequestDto requestDto, Long id) {
        Reply reply = replyRepository.findById(id).orElseThrow(
                ()-> new NullPointerException("댓글이 존재하지 않습니다."));


        reply.patchReply(requestDto);

    }

}
