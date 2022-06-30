package com.finalproject.breeding.model.board;

import com.finalproject.breeding.model.category.CommunityCategory;
import com.finalproject.breeding.model.category.ProvinceAreas;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Community {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @JoinColumn(name = "BOARDMAIN_ID")
    @OneToOne
    private BoardMain boardMain;

    @JoinColumn(name = "COMMUCATEGORY_ID")
    @ManyToOne
    private CommunityCategory communityCategory;

    @JoinColumn(name = "PROVINCEAREAS_ID")
    @ManyToOne
    private ProvinceAreas provinceAreas;

    @Column
    @NotNull
    private String title;
}
