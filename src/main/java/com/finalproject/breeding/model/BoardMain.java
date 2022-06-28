package com.finalproject.breeding.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.xml.soap.Name;

@Setter
@Getter
@Entity
public class BoardMain {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @JoinColumn(name = "BOARDKIND_ID")
    @ManyToOne
    private BoardKind boardKind;

    @JoinColumn(name = "USER_ID")
    @ManyToOne
    private User user;

}
