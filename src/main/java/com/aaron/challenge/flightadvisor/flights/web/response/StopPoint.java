package com.aaron.challenge.flightadvisor.flights.web.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StopPoint {

    private String city;

    private String country;

    private String airport;

    private String airportCode;
}
