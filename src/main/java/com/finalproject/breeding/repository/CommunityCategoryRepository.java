package com.finalproject.breeding.repository;

import com.finalproject.breeding.model.category.CommunityCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityCategoryRepository extends JpaRepository<CommunityCategory, Long> {


    CommunityCategory findByCategoryName(String name);
}
