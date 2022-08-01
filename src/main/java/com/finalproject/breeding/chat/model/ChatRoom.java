package com.finalproject.breeding.chat.model;

import com.finalproject.breeding.board.model.Together;
import com.finalproject.breeding.chat.dto.ChatRoomRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Entity
@NoArgsConstructor
public class ChatRoom {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @JoinColumn(name = "TOGETHER_ID")
    @OneToOne
    private Together togetherId;

    @JoinColumn(name = "TOGETHER_LIMIT_PEOPLE")
    @OneToOne
    private Together limitPeople;

    @JoinColumn(name = "TOGETHER_DDAY")
    @OneToOne
    private Together dday;

    @Column
    @NotEmpty
    private Long currentPeopleCnt;

    public void joinChat(ChatRoom chatRoom) {this.currentPeopleCnt = chatRoom.getCurrentPeopleCnt() + 1;}
    public void exitChat(ChatRoom chatRoom) {this.currentPeopleCnt = chatRoom.getCurrentPeopleCnt() - 1;}

    @Builder
    public ChatRoom(Together togetherId, Together dday, Long currentPeopleCnt, Together limitPeople) {
        this.togetherId = togetherId;
        this.dday = dday;
        this.currentPeopleCnt = currentPeopleCnt;
        this.limitPeople = limitPeople;
    }
}
