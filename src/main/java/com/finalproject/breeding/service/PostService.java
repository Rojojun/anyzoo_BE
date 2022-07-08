package com.finalproject.breeding.service;

import com.finalproject.breeding.dto.PostRequestDto;
import com.finalproject.breeding.dto.PostResponseDto;
import com.finalproject.breeding.model.category.PostNReelsCategory;
import com.finalproject.breeding.model.User;
import com.finalproject.breeding.model.board.BoardMain;
import com.finalproject.breeding.model.board.Post;
import com.finalproject.breeding.repository.BoardMainRepository;
import com.finalproject.breeding.repository.PostMapping;
import com.finalproject.breeding.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final BoardMainRepository boardMainRepository;
    public Map<String, Object> registPost(PostRequestDto postRequestDto,
                                          User user) {

       Post post = new Post(postRequestDto,boardMainRepository.
               save(new BoardMain(postRequestDto)),user);

        postRepository.save(post);

        Map<String, Object> data = new HashMap<>();
        data.put("postId", post.getId());
        data.put("boardMainId", post.getBoardMain().getId());
        return data;

    }
//    public List<PostListResponseDto> readAllPost(/*String postCategory*/) {
//        List<Post> postList;
//        List<PostListResponseDto> postListResponseDtoList = new ArrayList<>();
//
//        postList = postRepository.findAll();
//
//        for(Post post : postList) {
//            PostListResponseDto postListResponseDto = new PostListResponseDto(post);
//            postListResponseDtoList.add(postListResponseDto);
//        }
//
//        return postListResponseDtoList;
//    }

    @Transactional
    public PostResponseDto getPostDetail(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 게시글입니다.")
        );
        return new PostResponseDto(post);
    }


    @Transactional
    public Slice<PostMapping> readCategoryPost(Long page, String categoryname) {
        PageRequest pageRequest = PageRequest.of(Math.toIntExact(page), 4, Sort.by(Sort.Direction.DESC, "boardMain.createdAt"));
        if (categoryname.equals("cool")){
            return postRepository.findByPostNReelsCategory(pageRequest, PostNReelsCategory.COOL);
        } else if (categoryname.equals("cute")) {
            return postRepository.findByPostNReelsCategory(pageRequest, PostNReelsCategory.CUTE);
        }else if (categoryname.equals("comic")){
            return postRepository.findByPostNReelsCategory(pageRequest, PostNReelsCategory.COMIC);
        }else if (categoryname.equals("pretty")){
            return postRepository.findByPostNReelsCategory(pageRequest, PostNReelsCategory.PRETTY);
        } else if (categoryname.equals("all")) {
            return postRepository.findByOrderByIdDesc(pageRequest);
        } else {
            return null;
        }
    }


//
//    // 삭제하기
//    public void deletePost(Long id, String username) {
//        Post post = postRepository.findById(id)
//                .orElseThrow(IllegalArgumentException::new);
//        if (!Objects.equals(username, post.getBoardMain().getUser().getUsername())) {
//            throw new IllegalArgumentException("본인의 게시글만 삭제 할 수 있습니다.");
//        }
//
//        postRepository.deleteById(id);
//    }
//
//    @Transactional
//    public void updatePost(Long id, PostRequest4EditDto requestDto, String username) {
//        Post post = postRepository.findById(id)
//                .orElseThrow(IllegalAccessError::new);
//        if (!Objects.equals(username, post.getBoardMain().getUser().getUsername())) {
//            throw new IllegalArgumentException("본인의 게시글만 수정 할 수 있습니다.");
//        }
//
//        post.updatePost(requestDto);
//        postRepository.save(post);
//
//    }

}
