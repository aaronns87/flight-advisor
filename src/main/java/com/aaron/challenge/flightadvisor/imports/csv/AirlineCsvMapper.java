package com.aaron.challenge.flightadvisor.imports.csv;

import com.aaron.challenge.flightadvisor.airlines.Airline;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AirlineCsvMapper {

    Airline toAirline(AirlineCsv airlineCsv) {
        return Airline.builder()
                .id(UUID.randomUUID().toString())
                .externalId(airlineCsv.getExternalId()) // We are mapping external ids during CSV import since they are provided in CSV
                .code(airlineCsv.getCode())
                .build();
    }
}
