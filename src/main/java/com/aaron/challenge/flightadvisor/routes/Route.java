package com.aaron.challenge.flightadvisor.routes;

import com.aaron.challenge.flightadvisor.airlines.Airline;
import com.aaron.challenge.flightadvisor.airports.Airport;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Route {

    @Id
    @NaturalId
    @Column(nullable = false)
    private String id;

    @Column(nullable = false)
    private String airlineCode;

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    private Airline airline;

    @Column(nullable = false)
    private String sourceAirportCode;

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    private Airport sourceAirport;

    @Column(nullable = false)
    private String destinationAirportCode;

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    private Airport destinationAirport;

    @Column(nullable = false)
    private Boolean codeShare;

    @Column(nullable = false)
    private Integer stops;

    @Column(nullable = false)
    private String equipment;

    private Float price;
}
