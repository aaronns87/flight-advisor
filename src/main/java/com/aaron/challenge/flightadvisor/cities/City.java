package com.aaron.challenge.flightadvisor.cities;

import com.aaron.challenge.flightadvisor.comments.Comment;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class City {

    @Id
    @NaturalId
    @Column(nullable = false)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String country;

    @Column(insertable = false, updatable = false)
    private Integer mapping;

    @Singular
    @OneToMany(targetEntity = Comment.class, mappedBy = "city", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Comment> comments;
}
