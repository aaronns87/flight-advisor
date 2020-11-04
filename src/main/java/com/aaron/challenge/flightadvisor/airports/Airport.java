package com.aaron.challenge.flightadvisor.airports;

import com.aaron.challenge.flightadvisor.cities.City;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    private City city;

    @Column(length = 3)
    private String iata;

    @Column(length = 4)
    private String icao;

    private String latitude;

    private String longitude;

    private Integer altitude;

    private Double timeZone;

    @Enumerated(EnumType.STRING)
    private DST dst;

    private String tz;

    @Enumerated(EnumType.STRING)
    private Type type;

    private String source;
}
