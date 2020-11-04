package com.aaron.challenge.flightadvisor.airports.web;

import com.aaron.challenge.flightadvisor.airports.DST;
import com.aaron.challenge.flightadvisor.airports.Type;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class AirportCreate {

    @NotBlank
    @Schema(description = "Airport name", example = "London airport")
    private String name;

    @NotNull
    @Schema(description = "City Id", example = "1")
    private Long cityId;

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

    @Min(value = -12)
    @Max(value = 14)
    @Schema(description = "Time zone offset in hours (-12 - 14)", example = "+5")
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
