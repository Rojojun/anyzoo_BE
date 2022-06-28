package com.finalproject.breeding.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
public class Community {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @JoinColumn(name = "BOARDMAIN_ID")
    @ManyToOne
    private BoardMain boardMain;

    @JoinColumn(name = "COMMUCATEGORY_ID")
    @ManyToOne
    private CommuCategory commuCategory;

    @JoinColumn(name = "PROVINCEAREAS_ID")
    @ManyToOne
    private ProvinceAreas provinceAreas;

    @Column
    @NotNull
    private String title;
}
