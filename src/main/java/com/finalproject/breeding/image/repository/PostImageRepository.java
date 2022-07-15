package com.finalproject.breeding.image.repository;

import com.finalproject.breeding.image.model.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostImageRepository extends JpaRepository<PostImage, Long > {



    @Query("select p "+ "from PostImage p "+ "where p.url = :url ")
    PostImage findPostImageByUrl(String url);

    List<PostImage> findPostImageByPostId(Long postId);
}
