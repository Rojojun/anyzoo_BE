package com.finalproject.breeding.model.board;

import com.finalproject.breeding.dto.CommunityRequestDto;
import com.finalproject.breeding.model.category.CommunityCategory;
import com.finalproject.breeding.model.category.ProvinceAreas;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.net.UnknownServiceException;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Community {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @JoinColumn(name = "BOARDMAIN_ID")
    @OneToOne
    @NotNull
    private BoardMain boardMain;

    @JoinColumn(name = "COMMUCATEGORY_ID")
    @ManyToOne
    @NotNull
    private CommunityCategory communityCategory;

//    @JoinColumn(name = "PROVINCEAREAS_ID")
//    @ManyToOne
//    private ProvinceAreas provinceAreas;

    @Column
    @NotNull
    private String title;

    public Community(CommunityCategory communityCategory, CommunityRequestDto communityRequestDto, BoardMain boardMain){
        this.communityCategory = communityCategory;
        this.title = communityRequestDto.getTitle();
        this.boardMain = boardMain;
    }

    public void updaate(CommunityRequestDto communityRequestDto, BoardMain boardMain) {
        this.title = communityRequestDto.getTitle();
        this.boardMain = boardMain;
    }
}
