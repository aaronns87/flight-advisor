package com.aaron.challenge.flightadvisor.airlines.web;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AirlineResponse {

    @Schema(description = "Airline id")
    private String id;

    @Schema(description = "Airline external id")
    private Long externalId;

    @Schema(description = "Airline code", example = "2B")
    private String code;
}
