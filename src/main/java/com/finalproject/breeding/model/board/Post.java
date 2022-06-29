package com.finalproject.breeding.model.board;

import com.finalproject.breeding.model.category.PostCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.mapping.ToOne;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Post {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @JoinColumn(name = "BOARDMAIN_ID")
    @OneToOne
    private BoardMain boardMain;

    @JoinColumn(name = "POSTCATEGORY_ID")
    @ManyToOne
    private PostCategory postCategory;

}
