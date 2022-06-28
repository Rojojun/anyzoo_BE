package com.finalproject.breeding.model.category;

import com.finalproject.breeding.model.category.CityAreas;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
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
