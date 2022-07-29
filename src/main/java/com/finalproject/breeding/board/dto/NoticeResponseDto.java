package com.finalproject.breeding.board.dto;

import com.finalproject.breeding.board.model.Community;
import com.finalproject.breeding.board.model.Notice;
import com.finalproject.breeding.image.model.CommunityImage;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class NoticeResponseDto {
    private Long Id;
    private String title;
    private String content;
    private String img;
    private LocalDateTime dateTime;
    private String nickname;

    public NoticeResponseDto(Notice notice) {
        this.Id = notice.getId();
        this.title = notice.getTitle();
        this.content = notice.getContent();
        this.img = notice.getImg();
        this.dateTime = notice.getCreatedAt();
        this.nickname = notice.getUser().getNickname();
    }
}
