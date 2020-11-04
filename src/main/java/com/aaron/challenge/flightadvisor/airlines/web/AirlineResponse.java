package com.aaron.challenge.flightadvisor.airlines.web;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AirlineResponse {

    @Schema(description = "Airline id", example = "1")
    private Long id;

    @Schema(description = "Airline code", example = "2B")
    private String code;
}
