package com.finalproject.breeding.chat.model;

import com.finalproject.breeding.board.model.Together;
import com.finalproject.breeding.chat.dto.ChatRoomRequestDto;
import com.finalproject.breeding.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class ChatRoom {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @JoinColumn(name = "ROOM_NAME")
    @OneToOne
    private Together togetherName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private List<User> userList = new ArrayList<>();

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime modifiedAt;


   /* @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @JoinColumn(name = "TOGETHER_ID")
    @OneToOne
    private Together togetherId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private List<User> userList = new ArrayList<>();

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

    public ChatRoom(Together togetherId, Together dday, Long currentPeopleCnt, Together limitPeople, User writer, User applicant) {
        this.togetherId = togetherId;
        this.dday = dday;
        this.currentPeopleCnt = currentPeopleCnt;
        this.limitPeople = limitPeople;
        this.userList.add(writer);
        this.userList.add(applicant);
    }
*/
    public ChatRoom(Together together, User writer, User applicant) {
        this.togetherName = together;
        this.userList.add(writer);
        this.userList.add(applicant);
    }
}
