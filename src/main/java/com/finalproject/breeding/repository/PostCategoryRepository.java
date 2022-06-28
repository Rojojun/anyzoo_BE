package com.finalproject.breeding.repository;

import com.finalproject.breeding.model.category.PostCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCategoryRepository extends JpaRepository<PostCategory, Long> {
}
