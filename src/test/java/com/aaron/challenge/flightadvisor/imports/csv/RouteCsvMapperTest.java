package com.aaron.challenge.flightadvisor.imports.csv;

import com.aaron.challenge.flightadvisor.airlines.Airline;
import com.aaron.challenge.flightadvisor.airports.Airport;
import com.aaron.challenge.flightadvisor.cities.City;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class RouteCsvMapperTest {

    @InjectMocks
    private RouteCsvMapper routeCsvMapper;

    @Test
    public void toRoute_routeCsv_airline_sourceAirport_destinationAirport_route() {
        var airlineCsv = AirlineCsv.builder()
                .code("code")
                .build();

        var routeCsv = RouteCsv.builder()
                .sourceAirportCode("sourceAirportCode")
                .destinationAirportCode("destinationAirportCode")
                .airlineCsv(airlineCsv)
                .codeShare(true)
                .stops(1)
                .equipment("equipment")
                .price(1f)
                .build();

        var airline = Airline.builder()
                .externalId(1L)
                .id("id")
                .code("code")
                .build();

        var sourceAirport = Airport.builder()
                .externalId(2L)
                .city(City.builder()
                        .mapping(1)
                        .build())
                .build();

        var destinationAirport = Airport.builder()
                .externalId(3L)
                .city(City.builder()
                        .mapping(2)
                        .build())
                .build();

        var route = routeCsvMapper.toRoute(routeCsv, airline, sourceAirport, destinationAirport);

        assertThat(route.getId()).isNotBlank();
        assertThat(route.getSourceAirport()).isNotNull();
        assertThat(route.getSourceAirportCode()).isEqualTo("sourceAirportCode");
        assertThat(route.getDestinationAirport()).isNotNull();
        assertThat(route.getDestinationAirportCode()).isEqualTo("destinationAirportCode");
        assertThat(route.getCodeShare()).isTrue();
        assertThat(route.getStops()).isEqualTo(1);
        assertThat(route.getEquipment()).isEqualTo("equipment");
        assertThat(route.getPrice()).isEqualTo(1f);
        assertThat(route.getSourceCityMapping()).isEqualTo(1);
        assertThat(route.getDestinationCityMapping()).isEqualTo(2);
    }
}
