package com.aaron.challenge.flightadvisor.flights.web.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class FlightChain {

    private List<FlightStop> stops;

    private Float totalPrice;

    private Integer hops;
}
