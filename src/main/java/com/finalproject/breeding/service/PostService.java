package com.finalproject.breeding.service;

import com.finalproject.breeding.dto.PostListResponseDto;
import com.finalproject.breeding.dto.PostRequest4EditDto;
import com.finalproject.breeding.dto.PostRequestDto;
import com.finalproject.breeding.dto.PostResponseDto;
import com.finalproject.breeding.model.User;
import com.finalproject.breeding.model.board.BoardMain;
import com.finalproject.breeding.model.board.Post;
import com.finalproject.breeding.model.category.PostCategory;
import com.finalproject.breeding.repository.BoardMainRepository;
import com.finalproject.breeding.repository.PostCategoryRepository;
import com.finalproject.breeding.repository.PostRepository;
import com.finalproject.breeding.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final PostCategoryRepository postCategoryRepository;
    private final UserRepository userRepository;
    private final BoardMainRepository boardMainRepository;
    public PostResponseDto registPost(PostRequestDto postRequestDto, String username) {
        PostCategory postCategory = new PostCategory("test");
        postCategoryRepository.save(postCategory);
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new RuntimeException("아이디가 존재하지 않습니다.")
        );
        BoardMain boardMain = new BoardMain(postRequestDto, user);
        Post post = new Post(postCategory, boardMain);

        boardMainRepository.save(boardMain);
        postRepository.save(post);

        return new PostResponseDto(post);

    }

    public List<PostListResponseDto> readAllPost(/*String postCategory*/) {
        List<Post> postList;
        List<PostListResponseDto> postListResponseDtoList = new ArrayList<>();

        postList = postRepository.findAll();

        for(Post post : postList) {
            PostListResponseDto postListResponseDto = new PostListResponseDto(post);
            postListResponseDtoList.add(postListResponseDto);
        }

        return postListResponseDtoList;
    }

    public PostResponseDto getPostDetail(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 게시글입니다.")
        );
        return new PostResponseDto(post);
    }

    // 삭제하기
    public void deletePost(Long id, String username) {
        Post post = postRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        if (!Objects.equals(username, post.getBoardMain().getUser().getUsername())) {
            throw new IllegalArgumentException("본인의 게시글만 삭제 할 수 있습니다.");
        }

        postRepository.deleteById(id);
    }

    @Transactional
    public void updatePost(Long id, PostRequest4EditDto requestDto, String username) {
        Post post = postRepository.findById(id)
                .orElseThrow(IllegalAccessError::new);
        if (!Objects.equals(username, post.getBoardMain().getUser().getUsername())) {
            throw new IllegalArgumentException("본인의 게시글만 수정 할 수 있습니다.");
        }

        post.updatePost(requestDto);
        postRepository.save(post);

    }

    //
}
