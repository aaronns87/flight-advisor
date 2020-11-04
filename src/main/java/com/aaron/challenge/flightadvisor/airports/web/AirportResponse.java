package com.aaron.challenge.flightadvisor.airports.web;

import com.aaron.challenge.flightadvisor.airports.DST;
import com.aaron.challenge.flightadvisor.airports.Type;
import com.aaron.challenge.flightadvisor.cities.City;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AirportResponse {

    @Schema(description = "Airport id", example = "1")
    private Long id;

    @Schema(description = "Airport name", example = "London airport")
    private String name;

    @Schema(description = "City", example = "London")
    private City city;

    @Schema(description = "IATA - 3-letter IATA code", example = "GKA")
    private String iata;

    @Schema(description = "ICAO - 4-letter ICAO code", example = "AYGA")
    private String icao;

    @Schema(description = "Latitude", example = "-6.081689834590001")
    private String latitude;

    @Schema(description = "Longitude", example = "145.391998291")
    private String longitude;

    @Schema(description = "Altitude", example = "5282")
    private Integer altitude;

    @Schema(description = "Time zone offset in hours", example = "+5")
    private Double timeZone;

    @Schema(description = "Daylight saving time location.", example = "E (Europe)")
    private DST dst;

    @Schema(description = "Database time zone Timezone in (Olson) format", example = "America/Los_Angeles")
    private String tz;

    @Schema(description = "Type of the airport", example = "airport")
    private Type type;

    @Schema(description = "Source of this data", example = "OurAirports")
    private String source;
}
