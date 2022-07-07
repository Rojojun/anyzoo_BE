package com.finalproject.breeding.repository;

import com.finalproject.breeding.model.category.WithPostCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WithPostCategoryRepository extends JpaRepository<WithPostCategory, Long> {
    WithPostCategory findByName(String name);
}
