package com.finalproject.breeding.board.model;

import com.finalproject.breeding.board.dto.TogetherRequestDto;
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

    @JoinColumn(name = "USER_ID")
    @ManyToOne
    private User user;

    @JoinColumn
    @OneToOne
    private Room room;

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

    public Together(TogetherRequestDto togetherRequestDto, LocalDateTime date, BoardMain boardMain, Room room, User user){
        this.peopleCnt = 1;
        this.status = true;
        this.boardMain = boardMain;
        this.user = user;
        this.title = togetherRequestDto.getTitle();
        this.date = date;
        this.room = room;
        this.limitPeople = togetherRequestDto.getLimitPeople();;
    }


    public void joinTogether(Together together){
        this.peopleCnt = together.getPeopleCnt()+1;
    }
    public void outTogether(Together together){
        this.peopleCnt = together.getPeopleCnt()-1;
    }





}
