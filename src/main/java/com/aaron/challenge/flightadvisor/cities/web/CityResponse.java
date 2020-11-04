package com.aaron.challenge.flightadvisor.cities.web;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CityResponse {

    @Schema(description = "City id")
    private String id;

    @Schema(description = "Name", example = "London")
    private String name;

    @Schema(description = "Country", example = "England")
    private String country;
}
