package com.aaron.challenge.flightadvisor.comments;

import com.aaron.challenge.flightadvisor.cities.City;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Comment {

    @Id
    @NaturalId
    @Column(nullable = false)
    private String id;

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    private City city;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @Column(nullable = false)
    private LocalDateTime modifiedDate;
}
