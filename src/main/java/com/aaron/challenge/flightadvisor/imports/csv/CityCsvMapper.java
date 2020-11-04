package com.aaron.challenge.flightadvisor.imports.csv;

import com.aaron.challenge.flightadvisor.cities.City;
import com.aaron.challenge.flightadvisor.users.web.UserUpdate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CityCsvMapper {

    City toCity(CityCsv cityCsv) {
        return City.builder()
                .id(UUID.randomUUID().toString())
                .name(cityCsv.getName())
                .country(cityCsv.getCountry())
                .build();
    }
}
