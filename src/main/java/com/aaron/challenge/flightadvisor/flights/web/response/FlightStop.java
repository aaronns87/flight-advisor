package com.aaron.challenge.flightadvisor.flights.web.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FlightStop {

    private StopPoint embark;

    private StopPoint disembark;

    private String airlineCode;

    private String equipment;
}
