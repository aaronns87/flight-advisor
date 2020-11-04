package com.aaron.challenge.flightadvisor.cities.web;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CityUpdate {

    @Schema(description = "Name", example = "London")
    private String name;

    @Schema(description = "Country", example = "England")
    private String country;
}
