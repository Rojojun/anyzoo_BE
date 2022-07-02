package com.finalproject.breeding.model.category;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class CommunityCategory {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    @NotNull
    private String name;

    public CommunityCategory(String test){
        this.name = test;
    }
}
