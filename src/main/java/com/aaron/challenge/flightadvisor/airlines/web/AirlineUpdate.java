package com.aaron.challenge.flightadvisor.airlines.web;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AirlineUpdate {

    @Schema(description = "Airline code", example = "2B")
    private String code;
}
