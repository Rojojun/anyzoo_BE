package com.finalproject.breeding.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
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
