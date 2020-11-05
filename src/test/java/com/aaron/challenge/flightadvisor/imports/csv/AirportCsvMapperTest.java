package com.aaron.challenge.flightadvisor.imports.csv;

import com.aaron.challenge.flightadvisor.airports.DST;
import com.aaron.challenge.flightadvisor.airports.Type;
import com.aaron.challenge.flightadvisor.cities.City;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class AirportCsvMapperTest {

    @InjectMocks
    private AirportCsvMapper airportCsvMapper;

    @Test
    public void toAirport_airportCsv_city_airport() {
        var airportCsv = AirportCsv.builder()
                .externalId(1L)
                .name("name")
                .iata("iata")
                .icao("icao")
                .latitude("latitude")
                .longitude("longitude")
                .altitude(1)
                .timezone(1.0)
                .dst(DST.A)
                .tz("tz")
                .type(Type.AIRPORT)
                .source("source")
                .build();

        var city = City.builder()
                .id("id")
                .name("cityName")
                .country("cityCountry")
                .build();

        var airport = airportCsvMapper.toAirport(airportCsv, city);

        assertThat(airport.getExternalId()).isEqualTo(1L);
        assertThat(airport.getName()).isEqualTo("name");
        assertThat(airport.getCity().getName()).isEqualTo("cityName");
        assertThat(airport.getCity().getCountry()).isEqualTo("cityCountry");
        assertThat(airport.getIata()).isEqualTo("iata");
        assertThat(airport.getIcao()).isEqualTo("icao");
        assertThat(airport.getLatitude()).isEqualTo("latitude");
        assertThat(airport.getLongitude()).isEqualTo("longitude");
        assertThat(airport.getAltitude()).isEqualTo(1);
        assertThat(airport.getTimeZone()).isEqualTo(1.0);
        assertThat(airport.getDst()).isEqualTo(DST.A);
        assertThat(airport.getTz()).isEqualTo("tz");
        assertThat(airport.getType()).isEqualTo(Type.AIRPORT);
        assertThat(airport.getSource()).isEqualTo("source");
    }
}
