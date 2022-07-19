package com.finalproject.breeding.board.dto;

import com.finalproject.breeding.board.model.Notice;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class NoticeResponseDto {
    private Long noticeId;
    private String title;
    private String content;
    private String img;
    private LocalDateTime dateTime;
    private String nickname;

    public NoticeResponseDto(Notice notice){
        this.noticeId = notice.getId();
        this.title = notice.getTitle();
        this.content = notice.getContent();
        this.img = notice.getImg();
        this.dateTime = notice.getCreatedAt();
        this.nickname = notice.getUser().getNickname();
    }
}
