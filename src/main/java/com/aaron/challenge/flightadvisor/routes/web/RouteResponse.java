package com.aaron.challenge.flightadvisor.routes.web;

import com.aaron.challenge.flightadvisor.airlines.web.AirlineResponse;
import com.aaron.challenge.flightadvisor.airports.web.AirportResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RouteResponse {

    @Schema(description = "Route id")
    private String id;

    @Schema(description = "2-letter (IATA) or 3-letter (ICAO) code of the airline", example = "2B")
    private String airlineCode;

    @Schema(description = "Airline")
    private AirlineResponse airline;

    @Schema(description = "Source airport - 3-letter (IATA) or 4-letter (ICAO) code of the source airport", example = "AER")
    private String sourceAirportCode;

    @Schema(description = "Source Airport")
    private AirportResponse sourceAirport;

    @Schema(description = "Destination airport - 3-letter (IATA) or 4-letter (ICAO) code of the destination airport", example = "KZN")
    private String destinationAirportCode;

    @Schema(description = "Destination Airport Id")
    private AirportResponse destinationAirport;

    @Schema(description = "Is code share?", example = "true")
    private Boolean codeShare;

    @Schema(description = "Number of stops on route", example = "0")
    private Integer stops;

    @Schema(description = "Airplane equipment", example = "SF3")
    private String equipment;

    @Schema(description = "Price of ticket", example = "100.00")
    private Float price;
}
