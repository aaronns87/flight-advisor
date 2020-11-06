package com.aaron.challenge.flightadvisor.flights.web.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FlightSearchRequest {

    private FlightSearchCity source;

    private FlightSearchCity destination;

    private Integer maxHops;

    private Integer maxResults;
}
