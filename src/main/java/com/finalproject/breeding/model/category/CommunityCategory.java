package com.finalproject.breeding.model.category;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@Getter
@Entity
@NoArgsConstructor
public class CommunityCategory {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    @NotNull
    private String categoryName;
}
