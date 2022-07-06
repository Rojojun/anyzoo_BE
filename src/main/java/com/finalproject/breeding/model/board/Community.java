package com.finalproject.breeding.model.board;

import com.finalproject.breeding.dto.CommunityRequestDto;
import com.finalproject.breeding.model.category.CommunityCategory;
import com.finalproject.breeding.model.category.ProvinceAreas;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.net.UnknownServiceException;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Community {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column
    @NotNull
    private String title;
    @JoinColumn(name = "COMMUCATEGORY_ID", nullable = false)
    @ManyToOne
    private CommunityCategory communityCategory;
    @JoinColumn(name = "BOARDMAIN_ID")
    @OneToOne
    @NotNull
    private BoardMain boardMain;

//    @JoinColumn(name = "PROVINCEAREAS_ID")
//    @ManyToOne
//    private ProvinceAreas provinceAreas;



    public Community(CommunityCategory communityCategory, CommunityRequestDto communityRequestDto, BoardMain boardMain){
        this.communityCategory = communityCategory;
        this.title = communityRequestDto.getTitle();
        this.boardMain = boardMain;
    }

    public void update(CommunityRequestDto communityRequestDto, BoardMain boardMain) {
        this.title = communityRequestDto.getTitle();
        this.boardMain = boardMain;
    }
}
