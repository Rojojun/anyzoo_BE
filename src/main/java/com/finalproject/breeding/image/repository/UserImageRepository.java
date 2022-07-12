package com.finalproject.breeding.image.repository;

import com.finalproject.breeding.image.model.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserImageRepository extends JpaRepository<UserImage, Long> {
    UserImage findByUrl(String url);
    
}
