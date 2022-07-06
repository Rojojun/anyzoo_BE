package com.finalproject.breeding.model.category;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class BoardKind {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    @NotNull
    private String boardName;

}
