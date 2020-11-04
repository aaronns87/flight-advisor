package com.aaron.challenge.flightadvisor.routes.web;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class RouteCreate {

    @NotBlank
    @Schema(description = "2-letter (IATA) or 3-letter (ICAO) code of the airline", example = "2B")
    private String airlineCode;

    @NotBlank
    @Schema(description = "Airline Id")
    private String airlineId;

    @NotBlank
    @Schema(description = "Source airport - 3-letter (IATA) or 4-letter (ICAO) code of the source airport", example = "AER")
    private String sourceAirportCode;

    @NotBlank
    @Schema(description = "Source Airport Id")
    private String sourceAirportId;

    @NotBlank
    @Schema(description = "Destination airport - 3-letter (IATA) or 4-letter (ICAO) code of the destination airport", example = "KZN")
    private String destinationAirportCode;

    @NotBlank
    @Schema(description = "Destination Airport Id")
    private String destinationAirportId;

    @Schema(description = "Is code share?", example = "true")
    private Boolean codeShare;

    @Schema(description = "Number of stops on route", example = "0")
    private Integer stops;

    @Schema(description = "Airplane equipment", example = "SF3")
    private String equipment;

    @Schema(description = "Price of ticket", example = "100.00")
    private Float price;
}
