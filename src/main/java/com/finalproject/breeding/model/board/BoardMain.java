package com.finalproject.breeding.model.board;

import com.finalproject.breeding.model.ImgUrl;
import com.finalproject.breeding.model.Timestamped;
import com.finalproject.breeding.model.User;
import com.finalproject.breeding.model.category.BoardKind;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class BoardMain extends Timestamped {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @JoinColumn(name = "BOARDKIND_ID")
    @ManyToOne
    private BoardKind boardKind;

    @JoinColumn(name = "USER_ID")
    @ManyToOne
    private User user;

    @Column
    @NotNull
    private Long LikeCnt;

    @Column
    @NotNull
    private String content;

    @Column
    @NotNull
    private Long viewCnt;

    @OneToMany
    @JoinColumn(name = "IMG_URL")
    private List<ImgUrl> imgUrls;
}
