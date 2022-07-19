package com.finalproject.breeding.board.service;

import com.finalproject.breeding.board.dto.PostRequestDto;
import com.finalproject.breeding.board.dto.PostResponseDto;
import com.finalproject.breeding.etc.service.TierService;
import com.finalproject.breeding.etc.image.AwsS3Service;
import com.finalproject.breeding.etc.image.model.PostImage;
import com.finalproject.breeding.board.model.category.PostNReelsCategory;
import com.finalproject.breeding.etc.image.repository.PostImageRepository;
import com.finalproject.breeding.user.User;
import com.finalproject.breeding.board.model.BoardMain;
import com.finalproject.breeding.board.model.Post;
import com.finalproject.breeding.board.repository.BoardMainRepository;
import com.finalproject.breeding.board.repository.PostRepository;
import com.finalproject.breeding.user.UserValidator;
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
    private final PostImageRepository postImageRepository;
    private final AwsS3Service awsS3Service;
    private final TierService tierService;
    public Map<String, Object> registPost(PostRequestDto postRequestDto, User user) {
        List<PostImage> postImages = postRequestDto.getPostImages();

        Post post = new Post(postRequestDto,boardMainRepository.save(new BoardMain(postRequestDto)),user, postImages);

        imageUpdateToPost(postImages, postRepository.save(post));  //포스트와 포스트이미지 연관관계 맺어주기
        //postRepository.save(post);
        tierService.upTenExp(user);

        Map<String, Object> data = new HashMap<>();
        data.put("postId", post.getId());
        data.put("boardMainId", post.getBoardMain().getId());
        return data;

    }

    @Transactional
    public PostResponseDto getPostDetail(Long boardMainId) {
        Post post = postRepository.findByBoardMainId(boardMainId);
        return new PostResponseDto(post);
    }


    @Transactional
    public Slice<PostResponseDto> readCategoryPost(int page, String category) {
        PageRequest pageRequest = PageRequest.of(page, 3, Sort.by(Sort.Direction.DESC, "boardMain.createdAt"));
        switch (category) {
            case "cool":
                return postRepository.findPostByPostNReelsCategory(pageRequest, PostNReelsCategory.COOL);
            case "coollike":
                return postRepository.findPostByPostNReelsCategoryOrderByBoardMainLikeCntDesc(pageRequest, PostNReelsCategory.COOL);
            case "cute":
                return postRepository.findPostByPostNReelsCategory(pageRequest, PostNReelsCategory.CUTE);
            case "cutelike":
                return postRepository.findPostByPostNReelsCategoryOrderByBoardMainLikeCntDesc(pageRequest, PostNReelsCategory.CUTE);
            case "comic":
                return postRepository.findPostByPostNReelsCategory(pageRequest, PostNReelsCategory.COMIC);
            case "comiclike":
                return postRepository.findPostByPostNReelsCategoryOrderByBoardMainLikeCntDesc(pageRequest, PostNReelsCategory.COMIC);
            case "pretty":
                return postRepository.findPostByPostNReelsCategory(pageRequest, PostNReelsCategory.PRETTY);
            case "prettylike":
                return postRepository.findPostByPostNReelsCategoryOrderByBoardMainLikeCntDesc(pageRequest, PostNReelsCategory.PRETTY);
            case "all":
                return postRepository.findPostByOrderByIdDesc(pageRequest);
            case "alllike":
                return postRepository.findPostByOrderByBoardMainLikeCntDesc(pageRequest);
            default:
                return null;
        }
    }



    // 삭제하기
    public void deletePost(Long boardMainId, User user) {
        Post post = postRepository.findByBoardMainId(boardMainId);
        UserValidator.validateDelete4User(user, post.getUser().getId());   //로그인유저ID와 작성글의 유저ID 체크
        awsS3Service.removePostImages(post.getId());
        postRepository.delete(post);
    }

    @Transactional
    public Map<String, Object> updatePost(Long boardMainId, PostRequestDto requestDto, User user) {
        Post post = postRepository.findByBoardMainId(boardMainId);
        UserValidator.validateUpdate4User(user, post.getUser().getId()); //로그인유저ID와 작성글의 유저ID 체크
        post.getBoardMain().updatePost(requestDto);
        if (requestDto.getPostImages()!=null){
            awsS3Service.removePostImages(post.getId());
            List<PostImage> postImages = requestDto.getPostImages();
            post.updatePost(requestDto);
            imageUpdateToPost(postImages, post); //포스트와 포스트이미지 연관관계 맺어주기
        }

        Map<String, Object> data = new HashMap<>();
        data.put("postId", post.getId());
        data.put("boardMainId", post.getBoardMain().getId());
        return data;
    }

    public void imageUpdateToPost(List<PostImage> postImages, Post post) {
        for (PostImage postimage : postImages) {
            postimage.updateToPost(post);
        }
    }

}
