package com.finalproject.breeding.model.category;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PostCategory {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    @NotNull
    private String categoryName;
}
