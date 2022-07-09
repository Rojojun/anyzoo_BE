package com.finalproject.breeding.board.model.category;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class ProvinceAreas {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @JoinColumn(name = "CITYAREAS_ID")
    @ManyToOne
    private CityAreas cityAreas;

    @Column
    @NotNull
    private String adm_code;

    @Column
    @NotNull
    private String name;

    @Column
    @NotNull
    private String version;
}
