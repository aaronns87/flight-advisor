package com.aaron.challenge.flightadvisor.imports.csv;

import com.aaron.challenge.flightadvisor.airports.Airport;
import com.aaron.challenge.flightadvisor.airports.AirportService;
import com.aaron.challenge.flightadvisor.cities.City;
import com.aaron.challenge.flightadvisor.cities.CityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor

@Component
public class AirportCsvPersist {

    private final AirportService airportService;
    private final AirportCsvMapper airportCsvMapper;

    private final CityService cityService;
    private final CityCsvMapper cityCsvMapper;

    void store(AirportCsv airportCsv) {
        try {
            createInDb(
                    map(airportCsv)
            );
        } catch (Exception e) {
            log.warn("Skipping import of airport {} due to error.", airportCsv.toString(), e);
        }
    }

    private Airport map(AirportCsv airportCsv) {
        return airportCsvMapper.toAirport(
                airportCsv,
                findOrCreateCity(
                        airportCsv.getCityCsv()
                )
        );
    }

    private void createInDb(Airport airport) {
        airportService.create(airport);
    }

    private City findOrCreateCity(CityCsv cityCsv) {
        return findCity(
                cityCsv.getName(),
                cityCsv.getCountry()
        ).orElseGet(
                () -> create(cityCsv)
        );
    }

    private Optional<City> findCity(String name, String country) {
        return cityService.findByNameAndCountry(name, country);
    }

    private City create(CityCsv cityCsv) {
        return createInDb(
                map(cityCsv)
        );
    }

    private City map(CityCsv cityCsv) {
        return cityCsvMapper.toCity(cityCsv);
    }

    private City createInDb(City city) {
        return cityService.create(city);
    }
}
