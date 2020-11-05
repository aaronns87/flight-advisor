package com.aaron.challenge.flightadvisor.imports.csv;

import com.aaron.challenge.flightadvisor.airports.Airport;
import com.aaron.challenge.flightadvisor.airports.AirportService;
import com.aaron.challenge.flightadvisor.cities.City;
import com.aaron.challenge.flightadvisor.cities.CityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AirportCsvPersistTest {

    @InjectMocks
    private AirportCsvPersist airportCsvPersist;

    @Mock
    private AirportService airportService;

    @Mock
    private AirportCsvMapper airportCsvMapper;

    @Mock
    private CityService cityService;

    @Mock
    private CityCsvMapper cityCsvMapper;

    @Test
    public void store_airportCsv_cityFound() {
        var airportCsv = AirportCsv.builder()
                .cityCsv(CityCsv.builder().build())
                .build();

        when(airportCsvMapper.toAirport(any(), any())).thenReturn(Airport.builder().build());
        when(cityService.findByNameAndCountry(any(), any())).thenReturn(Optional.of(City.builder().build()));

        airportCsvPersist.store(airportCsv);

        verify(airportService).create(any());
    }

    @Test
    public void store_airportCsv_cityNotFound() {
        var airportCsv = AirportCsv.builder()
                .cityCsv(CityCsv.builder().build())
                .build();

        when(airportCsvMapper.toAirport(any(), any())).thenReturn(Airport.builder().build());
        when(cityService.findByNameAndCountry(any(), any())).thenReturn(Optional.empty());
        when(cityCsvMapper.toCity(any())).thenReturn(City.builder().build());
        when(cityService.create(any())).thenReturn(City.builder().build());

        airportCsvPersist.store(airportCsv);

        verify(airportService).create(any());
    }
}
