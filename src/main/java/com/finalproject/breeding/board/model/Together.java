package com.finalproject.breeding.board.model;

import com.finalproject.breeding.board.dto.TogetherRequestDto;
import com.finalproject.breeding.board.model.category.PostNReelsCategory;
import com.finalproject.breeding.board.model.category.ProvinceAreas;
import com.finalproject.breeding.board.model.category.TogetherCategory;
import com.finalproject.breeding.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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

//    @JoinColumn
//    @OneToOne
//    private Room room;

    @JoinColumn(name = "BOARDMAIN_ID")
    @OneToOne
    private BoardMain boardMain;

    @JoinColumn(nullable = false)
    @Enumerated(EnumType.STRING)
    private TogetherCategory togetherCategory;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private int limitPeople;

    @Column(nullable = false)
    private int peopleCnt;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private boolean status;

    public Together(TogetherRequestDto togetherRequestDto, LocalDateTime date, BoardMain boardMain, User user){
        this.peopleCnt = 1;
        this.status = true;
        this.boardMain = boardMain;
        this.user = user;
        this.title = togetherRequestDto.getTitle();
        this.date = date;
        //this.room = room;
        this.limitPeople = togetherRequestDto.getLimitPeople();
        switch (togetherRequestDto.getCategoryName()) {
            case "walk":
                this.togetherCategory = TogetherCategory.WALK;
                break;
            case "buy":
                this.togetherCategory = TogetherCategory.BUY;
                break;
            case "cafe":
                this.togetherCategory = TogetherCategory.CAFE;
                break;
            case "etc":
                this.togetherCategory = TogetherCategory.ETC;
                break;
        }
    }


    public void joinTogether(Together together){
        this.peopleCnt = together.getPeopleCnt()+1;
    }
    public void outTogether(Together together){
        this.peopleCnt = together.getPeopleCnt()-1;
    }





}
