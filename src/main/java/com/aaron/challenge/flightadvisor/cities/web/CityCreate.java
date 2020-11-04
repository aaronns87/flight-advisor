package com.aaron.challenge.flightadvisor.cities.web;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class CityCreate {

    @NotBlank
    @Schema(description = "Name", example = "London")
    private String name;

    @NotBlank
    @Schema(description = "Country", example = "England")
    private String country;
}
