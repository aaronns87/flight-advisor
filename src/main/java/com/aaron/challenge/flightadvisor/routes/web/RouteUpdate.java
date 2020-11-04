package com.aaron.challenge.flightadvisor.routes.web;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RouteUpdate {

    @Schema(description = "2-letter (IATA) or 3-letter (ICAO) code of the airline", example = "2B")
    private String airlineCode;

    @Schema(description = "Source airport - 3-letter (IATA) or 4-letter (ICAO) code of the source airport", example = "AER")
    private String sourceAirportCode;

    @Schema(description = "Destination airport - 3-letter (IATA) or 4-letter (ICAO) code of the destination airport", example = "KZN")
    private String destinationAirportCode;

    @Schema(description = "Is code share?", example = "true")
    private Boolean codeShare;

    @Schema(description = "Number of stops on route", example = "0")
    private Integer stops;

    @Schema(description = "Airplane equipment", example = "SF3")
    private String equipment;

    @Schema(description = "Price of ticket", example = "100.00")
    private Float price;
}
