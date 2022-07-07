package com.finalproject.breeding.model.category;

import com.finalproject.breeding.dto.BoardKindDto;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Entity
@Setter
@NoArgsConstructor
public class BoardKind {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(unique = true)
    @NotNull
    private String boardName;

    public BoardKind(String boardName){
        this.boardName = boardName;
    }
}
