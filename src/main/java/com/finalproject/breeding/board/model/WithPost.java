package com.finalproject.breeding.board.model;

import com.finalproject.breeding.board.dto.WithPostRequestDto;
import com.finalproject.breeding.board.model.category.ProvinceAreas;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class WithPost {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @JoinColumn(name = "BOARDMAIN_ID")
    @OneToOne
    private BoardMain boardMain;

    @JoinColumn(name = "WITHPOSTCATEGORY_ID")
    @ManyToOne
    private WithPostCategory withPostCategory;

    @JoinColumn(name = "PROVINCEAREA_ID")
    @ManyToOne
    private ProvinceAreas provinceAreas;

    @Column
    @NotNull
    private String title;

    @Column
    @NotNull
    private String date;

    @Column
    @NotNull
    private Long limitPeople;

    @Column
    @NotNull
    private String location;

    @Column
    @NotNull
    private Long peopleCnt;

    public WithPost(WithPostCategory withPostCategory, WithPostRequestDto withPostRequestDto, BoardMain boardMain){
        this.withPostCategory = withPostCategory;
        this.title = withPostRequestDto.getTitle();
        this.date = withPostRequestDto.getDate();
        this.limitPeople = withPostRequestDto.getLimitPeople();
        this.location = withPostRequestDto.getDate();
        this.peopleCnt = 1L;
        this.boardMain = boardMain;
    }
}
