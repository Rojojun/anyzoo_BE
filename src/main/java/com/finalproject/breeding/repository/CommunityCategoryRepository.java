package com.finalproject.breeding.repository;

import com.finalproject.breeding.model.Comment;
import com.finalproject.breeding.model.category.CommunityCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityCategoryRepository extends JpaRepository<CommunityCategory, Long> {
}
