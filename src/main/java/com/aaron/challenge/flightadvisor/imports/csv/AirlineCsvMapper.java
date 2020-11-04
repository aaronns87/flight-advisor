package com.aaron.challenge.flightadvisor.imports.csv;

import com.aaron.challenge.flightadvisor.airlines.Airline;
import org.springframework.stereotype.Component;

@Component
public class AirlineCsvMapper {

    Airline toAirline(AirlineCsv airlineCsv) {
        return Airline.builder()
                .id(airlineCsv.getId()) // We are mapping ids during CSV import since they are provided in CSV
                .code(airlineCsv.getCode())
                .build();
    }
}
