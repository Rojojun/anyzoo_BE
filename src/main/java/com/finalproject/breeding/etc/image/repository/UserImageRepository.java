package com.finalproject.breeding.etc.image.repository;

import com.finalproject.breeding.etc.image.model.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserImageRepository extends JpaRepository<UserImage, Long> {
    UserImage findByUrl(String url);

    UserImage findByUserId(Long userId);
}
