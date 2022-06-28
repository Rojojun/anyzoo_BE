package com.finalproject.breeding.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
public class ImgUrl {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

   @Column
    private Long boardMainId;

    @Column
    @NotNull
    private String Field;
}
