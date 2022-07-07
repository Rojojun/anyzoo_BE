package com.finalproject.breeding.board.model;


import com.finalproject.breeding.board.model.BoardMain;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Reels {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @JoinColumn(name = "BOARDMAIN_ID")
    @OneToOne
    private BoardMain boardMain;

   /* @JoinColumn(name = "POSTCATEGORY_ID")
    @ManyToOne
    private PostNReelsCategory postNReelsCategory;*/

    @Column
    @NotNull
    private String video;

    @Column
    @NotNull
    private String titleImg;
}
