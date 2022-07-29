package com.finalproject.breeding.board.model;

import com.finalproject.breeding.board.dto.TogetherRequestDto;
import com.finalproject.breeding.board.model.category.ProvinceAreas;
import com.finalproject.breeding.image.model.TogetherImage;
import com.finalproject.breeding.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Together {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @JoinColumn
    @ManyToOne
    private ProvinceAreas provinceAreas;

    @JoinColumn(name = "USER_ID")
    @ManyToOne
    private User user;

    @JoinColumn(name = "BOARDMAIN_ID")
    @OneToOne
    private BoardMain boardMain;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private int limitPeople;

    @Column(nullable = false)
    private int peopleCnt;

    @Column(nullable = false)
    private LocalDateTime dday;

    @Column(nullable = false)
    private boolean status;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "TOGETHER_ID")
    private List<TogetherImage> togetherImages;

    @Builder
    public Together(ProvinceAreas provinceAreas, User user, BoardMain boardMain, String title, int limitPeople, int peopleCnt, LocalDateTime dday, List<TogetherImage> togetherImages){
        this.provinceAreas = provinceAreas;
        this.user = user;
        this.boardMain = boardMain;
        this.title = title;
        this.limitPeople = limitPeople;
        this.peopleCnt = peopleCnt;
        this.dday = dday;
        this.status = false;
        this.togetherImages = togetherImages;
    }


    public void joinTogether(Together together){
        this.peopleCnt = together.getPeopleCnt()+1;
    }
    public void outTogether(Together together){
        this.peopleCnt = together.getPeopleCnt()-1;
    }




    public void updateTogether(TogetherRequestDto togetherRequestDto) {
        this.title = togetherRequestDto.getTitle();
        this.limitPeople = togetherRequestDto.getLimitPeople();
        this.dday = togetherRequestDto.getDday();
    }
}
