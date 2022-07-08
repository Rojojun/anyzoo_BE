package com.finalproject.breeding.image.repository;

import com.finalproject.breeding.image.model.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostImageRepository extends JpaRepository<PostImage, Long > {


    List<Long> findPostImageIdByPostId(Long postId);
}
