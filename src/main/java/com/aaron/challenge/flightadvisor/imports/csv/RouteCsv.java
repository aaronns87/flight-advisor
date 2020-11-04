package com.aaron.challenge.flightadvisor.imports.csv;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class RouteCsv {

    private AirlineCsv airlineCsv;

    private String sourceAirportCode;

    private Long sourceAirportId;

    private String destinationAirportCode;

    private Long destinationAirportId;

    private Boolean codeShare;

    private Integer stops;

    private String equipment;

    private Float price;
}
