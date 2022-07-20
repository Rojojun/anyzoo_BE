package com.finalproject.breeding.board.service;

import com.finalproject.breeding.board.dto.PostRequest4EditDto;
import com.finalproject.breeding.board.dto.PostRequestDto;
import com.finalproject.breeding.board.dto.PostResponseDto;
import com.finalproject.breeding.board.model.category.BoardKind;
import com.finalproject.breeding.etc.service.TierService;
import com.finalproject.breeding.image.AwsS3Service;
import com.finalproject.breeding.image.ImageRequestDto;
import com.finalproject.breeding.image.model.PostImage;
import com.finalproject.breeding.board.model.category.PostNReelsCategory;
import com.finalproject.breeding.error.CustomException;
import com.finalproject.breeding.error.ErrorCode;
import com.finalproject.breeding.image.repository.PostImageRepository;
import com.finalproject.breeding.user.User;
import com.finalproject.breeding.board.model.BoardMain;
import com.finalproject.breeding.board.model.Post;
import com.finalproject.breeding.board.repository.BoardMainRepository;
import com.finalproject.breeding.board.repository.PostRepository;
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

        imageUpdateToPost(postImages, postRepository.save(post));
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
    public Slice<PostResponseDto> readCategoryPost(Long page, String category) {
        PageRequest pageRequest = PageRequest.of(Math.toIntExact(page), 5, Sort.by(Sort.Direction.DESC, "boardMain.createdAt"));
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
        BoardMain deleteBoardMainId = post.getBoardMain();
        if (!Objects.equals(user.getId(), post.getUser().getId())) {
            throw new CustomException(ErrorCode.POST_DELETE_WRONG_ACCESS);
        }
        awsS3Service.removePostImages(post.getId());
        postRepository.delete(post);
        boardMainRepository.delete(deleteBoardMainId);

    }

    @Transactional
    public Map<String, Object> updatePost(Long boardMainId, PostRequest4EditDto requestDto, User user) {
        Post post = postRepository.findByBoardMainId(boardMainId);
        if (!Objects.equals(user.getId(), post.getUser().getId())) {
            throw new CustomException(ErrorCode.POST_UPDATE_WRONG_ACCESS);
        }
        List<PostImage> postImages = requestDto.getPostImages();

        post.updatePost(requestDto, post.getBoardMain(), post.getPostImage());

        imageUpdateToPost(postImages, post);

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
