package com.finalproject.breeding.controller;

import com.finalproject.breeding.model.category.BoardKind;
import com.finalproject.breeding.model.category.CommunityCategory;
import com.finalproject.breeding.model.category.PostCategory;
import com.finalproject.breeding.repository.BoardKindRepository;
import com.finalproject.breeding.repository.CommunityCategoryRepository;
import com.finalproject.breeding.repository.PostCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final BoardKindRepository boardKindRepository;
    private final PostCategoryRepository postCategoryRepository;
    private final CommunityCategoryRepository communityCategoryRepository;

    @PostMapping("/api/admin/boardkind/{boardName}")
    public void registBoardKind(@PathVariable String boardName){
        boardKindRepository.save(BoardKind.builder().boardName(boardName).build());}

    @PostMapping("/api/admin/postcategory/{name}")
    public void registPostCategory(@PathVariable String name){
        postCategoryRepository.save(PostCategory.builder().categoryName(name).build());}

    @PostMapping("/api/admin/communitycategory/{name}")
    public void registCommunityCategory(@PathVariable String name){
        communityCategoryRepository.save(CommunityCategory.builder().categoryName(name).build());}
}
