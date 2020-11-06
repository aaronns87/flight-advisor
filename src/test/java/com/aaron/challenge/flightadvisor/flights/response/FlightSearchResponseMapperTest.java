package com.aaron.challenge.flightadvisor.flights.response;

import com.aaron.challenge.flightadvisor.airports.Airport;
import com.aaron.challenge.flightadvisor.cities.City;
import com.aaron.challenge.flightadvisor.flights.web.response.FlightSearchResponseMapper;
import com.aaron.challenge.flightadvisor.routes.Route;
import com.aaron.challenge.flightadvisor.routes.RouteService;
import com.aaron.challenge.flightadvisor.routes.cheapest.CheapestFlightChain;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FlightSearchResponseMapperTest {

    @InjectMocks
    private FlightSearchResponseMapper flightSearchResponseMapper;

    @Mock
    private RouteService routeService;

    @Test
    public void flightSearchResponse_cheapestFlightChains_flightSearchResponse() {
        var cheapestFlightChain = new CheapestFlightChain("routes", 1F, 1);

        var route = Route.builder()
                .sourceAirportCode("embarkAirportCode")
                .sourceAirport(Airport.builder()
                        .city(City.builder()
                                .name("embarkCity")
                                .country("embarkCountry")
                                .build())
                        .name("embarkAirport")
                        .build())
                .destinationAirportCode("disembarkAirportCode")
                .destinationAirport(Airport.builder()
                        .city(City.builder()
                                .name("disembarkCity")
                                .country("disembarkCountry")
                                .build())
                        .name("disembarkAirport")
                        .build())
                .airlineCode("airlineCode")
                .equipment("equipment")
                .build();

        when(routeService.findById(eq("routes"))).thenReturn(Optional.of(route));

        var response = flightSearchResponseMapper.flightSearchResponse(List.of(cheapestFlightChain));

        assertThat(response.getFound()).isEqualTo(1);
        assertThat(response.getChains()).hasSize(1);
        assertThat(response.getChains().get(0).getTotalPrice()).isEqualTo(1F);
        assertThat(response.getChains().get(0).getHops()).isEqualTo(1);
        assertThat(response.getChains().get(0).getStops()).hasSize(1);
        assertThat(response.getChains().get(0).getStops().get(0).getAirlineCode()).isEqualTo("airlineCode");
        assertThat(response.getChains().get(0).getStops().get(0).getEquipment()).isEqualTo("equipment");
        assertThat(response.getChains().get(0).getStops().get(0).getEmbark().getCity()).isEqualTo("embarkCity");
        assertThat(response.getChains().get(0).getStops().get(0).getEmbark().getCountry()).isEqualTo("embarkCountry");
        assertThat(response.getChains().get(0).getStops().get(0).getEmbark().getAirport()).isEqualTo("embarkAirport");
        assertThat(response.getChains().get(0).getStops().get(0).getEmbark().getAirportCode()).isEqualTo("embarkAirportCode");
        assertThat(response.getChains().get(0).getStops().get(0).getDisembark().getCity()).isEqualTo("disembarkCity");
        assertThat(response.getChains().get(0).getStops().get(0).getDisembark().getCountry()).isEqualTo("disembarkCountry");
        assertThat(response.getChains().get(0).getStops().get(0).getDisembark().getAirport()).isEqualTo("disembarkAirport");
        assertThat(response.getChains().get(0).getStops().get(0).getDisembark().getAirportCode()).isEqualTo("disembarkAirportCode");
    }
}
