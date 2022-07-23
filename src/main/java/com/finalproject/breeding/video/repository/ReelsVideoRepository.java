package com.finalproject.breeding.video.repository;

import com.finalproject.breeding.video.model.ReelsVideo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReelsVideoRepository extends JpaRepository<ReelsVideo, Long> {
    ReelsVideo findByUrl(String url);
}
