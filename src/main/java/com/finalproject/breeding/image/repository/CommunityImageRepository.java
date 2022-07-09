package com.finalproject.breeding.image.repository;

import com.finalproject.breeding.image.model.CommunityImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityImageRepository extends JpaRepository<CommunityImage, Long> {
    CommunityImage findByUrl(String url);
}
