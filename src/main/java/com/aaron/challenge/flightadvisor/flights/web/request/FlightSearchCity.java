package com.aaron.challenge.flightadvisor.flights.web.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FlightSearchCity {

    private String id;

    private String name;

    private String country;
}
