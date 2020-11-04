package com.aaron.challenge.flightadvisor.imports.csv;

import com.aaron.challenge.flightadvisor.airports.DST;
import com.aaron.challenge.flightadvisor.airports.Type;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class AirportCsv {

    private Long id;

    private String name;

    private CityCsv cityCsv;

    private String iata;

    private String icao;

    private String latitude;

    private String longitude;

    private Integer altitude;

    private Double timezone;

    private DST dst;

    private String tz;

    private Type type;

    private String source;
}
