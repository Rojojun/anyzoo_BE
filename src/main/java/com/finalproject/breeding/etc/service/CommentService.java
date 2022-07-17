package com.finalproject.breeding.etc.service;

import com.finalproject.breeding.board.model.BoardMain;
import com.finalproject.breeding.board.repository.BoardMainRepository;
import com.finalproject.breeding.dto.CommentResponseDto;
import com.finalproject.breeding.etc.dto.CommentRequestDto;
import com.finalproject.breeding.etc.dto.MyDto;
import com.finalproject.breeding.error.ErrorCode;
import com.finalproject.breeding.etc.model.Comment;
import com.finalproject.breeding.etc.repository.CommentRepository;
import com.finalproject.breeding.etc.dto.CommentMapping;
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
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    private final BoardMainRepository boardMainRepository;
    private final UserRepository userRepository;

    //댓글 작성
    @Transactional
    public ResponseEntity<MyDto> createComment(CommentRequestDto requestDto, Long boardMainId, String username) {

        MyDto dto = new MyDto();
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        BoardMain boardMain = boardMainRepository.findById(boardMainId).orElseThrow(
                () -> new NullPointerException("게시글이 존재하지 않습니다."));

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new NullPointerException("유저가 존재하지 않습니다."));

        Comment comment = new Comment(requestDto, boardMain, user);

        Long commentId = commentRepository.save(comment).getId();
        boardMain.plusCommentCnt(boardMain);



        dto.setStatus(ErrorCode.OK);
        dto.setData(commentId);
        dto.setMessage("댓글 등록!");
        return new ResponseEntity<>(dto, header, HttpStatus.OK);


    }


    //댓글 삭제
    @Transactional
    public ResponseEntity<MyDto> deleteComment(Long commentId, Long boardMainId) {
        BoardMain boardMain = boardMainRepository.findById(boardMainId).orElseThrow(
                () -> new NullPointerException("게시글이 존재하지 않습니다."));

        MyDto dto = new MyDto();
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        String userId = SecurityUtil.getCurrentUsername(); //현제 로그인한 유저 pk
        Optional<Comment> a =commentRepository.findById(commentId);
        String userId1 =a.get().getUser().getUsername();

        if (Objects.equals(userId, userId1)) {   //댓글의 닉네임와 일치한다면

            boardMain.minusCommentCnt(boardMain);
            commentRepository.deleteById(commentId);

            dto.setStatus(ErrorCode.OK);
            dto.setData(commentId);
            dto.setMessage("댓글 삭제!");
            return new ResponseEntity<>(dto, header, HttpStatus.OK);
        }else{
            dto.setStatus(ErrorCode.COMMENT_WRONG_INPUT);
            dto.setData(commentId);
            dto.setMessage("사용자의 댓글이 아닙니다!");
            return new ResponseEntity<>(dto,header, HttpStatus.BAD_REQUEST);
        }

    }

    //댓글 수정
    @Transactional
    public ResponseEntity<MyDto> patchComment(CommentRequestDto requestDto, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new NullPointerException("댓글이 존재하지 않습니다."));

        MyDto dto = new MyDto();
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        String userId = SecurityUtil.getCurrentUsername(); //현제 로그인한 유저 pk
        Optional<Comment> a =commentRepository.findById(commentId);
        String userId1 =a.get().getUser().getUsername();

        if (Objects.equals(userId, userId1)) {   //댓글의 닉네임와 일치한다면
            comment.updateComment(requestDto);
            dto.setStatus(ErrorCode.OK);
            dto.setData(commentId);
            dto.setMessage("댓글 수정!");
            return new ResponseEntity<>(dto, header, HttpStatus.OK);
        }else{
            dto.setStatus(ErrorCode.COMMENT_WRONG_INPUT);
            dto.setData(commentId);
            dto.setMessage("사용자의 댓글이 아닙니다!");
            return new ResponseEntity<>(dto,header, HttpStatus.BAD_REQUEST);
        }

    }

    //댓글 불러오기
    @Transactional
    public CommentResponseDto getAllCommnet(Long boardMainId, Long page) {
        PageRequest pageRequest = PageRequest.of(Math.toIntExact(page), 10, Sort.by(Sort.Direction.DESC, "Id"));

        Slice<CommentMapping> comments = commentRepository.findByBoardMainId(pageRequest, boardMainId);
        CommentResponseDto commentResponseDto = new CommentResponseDto(comments.getContent(), comments.isLast());
        return commentResponseDto;
    }


}
