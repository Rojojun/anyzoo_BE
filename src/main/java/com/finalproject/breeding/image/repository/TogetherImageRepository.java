package com.finalproject.breeding.image.repository;

import com.finalproject.breeding.image.model.TogetherImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TogetherImageRepository extends JpaRepository<TogetherImage, Long> {
    List<TogetherImage> findByTogetherId(Long togetherId);
}
