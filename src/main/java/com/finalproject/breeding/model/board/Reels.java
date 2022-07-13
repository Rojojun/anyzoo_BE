package com.finalproject.breeding.model.board;

import com.finalproject.breeding.model.category.PostCategory;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @JoinColumn(name = "POSTCATEGORY_ID")
    @ManyToOne
    private PostCategory postCategory;

    @Column
    @NotNull
    private String video;

    @Column
    @NotNull
    private String titleImg;
}