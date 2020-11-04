package com.aaron.challenge.flightadvisor.imports.csv;

import com.aaron.challenge.flightadvisor.cities.City;
import org.springframework.stereotype.Component;

@Component
public class CityCsvMapper {

    City toCity(CityCsv cityCsv) {
        return City.builder()
                .name(cityCsv.getName())
                .country(cityCsv.getCountry())
                .build();
    }
}
