package com.finalproject.breeding.repository;

import com.finalproject.breeding.model.User;
import com.finalproject.breeding.model.board.Community;
import com.finalproject.breeding.model.category.CommunityCategory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface CommunityRepository extends JpaRepository<Community, Long> {

    @Query("select c " + "from Community c " + "where c.communityCategory.id = : communityCategoryId " + "order by c.boardMain.createdAt desc ")
    Slice<Community> findByCommunityCategoryIdOrderByCreatedAtDesc(PageRequest pageRequest, Long communityCategoryId);

    @Query("select c " + "from Community c " + "order by c.boardMain.likeCnt desc ")
    Slice<Community> findAllByOrderByLikeCntDesc(PageRequest pageRequest);

    @Query("select c " + "from Community c " + "order by c.boardMain.likeCnt desc ")
    Slice<Community> findByCommunityCategory(PageRequest pageRequest, CommunityCategory communityCategory);


}
