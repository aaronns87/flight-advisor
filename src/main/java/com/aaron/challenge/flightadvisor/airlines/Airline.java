package com.aaron.challenge.flightadvisor.airlines;

import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Airline {

    @Id
    @NaturalId
    @Column(nullable = false)
    private String id;

    @Column(unique = true)
    private Long externalId;

    @Column(nullable = false)
    private String code;
}
