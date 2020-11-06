package com.aaron.challenge.flightadvisor.flights;

import com.aaron.challenge.flightadvisor.cities.City;
import com.aaron.challenge.flightadvisor.cities.CityService;
import com.aaron.challenge.flightadvisor.flights.web.request.FlightSearchCity;
import com.aaron.challenge.flightadvisor.flights.web.request.FlightSearchRequest;
import com.aaron.challenge.flightadvisor.flights.web.response.FlightSearchResponse;
import com.aaron.challenge.flightadvisor.flights.web.response.FlightSearchResponseMapper;
import com.aaron.challenge.flightadvisor.routes.cheapest.CheapestFlightChain;
import com.aaron.challenge.flightadvisor.routes.cheapest.CheapestFlightsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FlightServiceTest {

    @InjectMocks
    private FlightService flightService;

    @Mock
    private CheapestFlightsService cheapestFlightsService;

    @Mock
    private CityService cityService;

    @Mock
    private FlightSearchResponseMapper flightSearchResponseMapper;

    @Test
    public void searchCheapestRoute_flightSearchRequest_flightSearchResponse() {
        var sourceCity = City.builder()
                .id("sourceCity")
                .build();
        var destinationCity = City.builder()
                .id("destinationCity")
                .build();

        var cheapestFlightChains = new ArrayList<CheapestFlightChain>();

        var response = FlightSearchResponse.builder()
                .build();

        when(cityService.findById(eq("sourceCity"))).thenReturn(Optional.of(sourceCity));
        when(cityService.findById(eq("destinationCity"))).thenReturn(Optional.of(destinationCity));

        when(cheapestFlightsService.cheapestFlightRoutes(eq(sourceCity), eq(destinationCity), any(), any())).thenReturn(cheapestFlightChains);
        when(flightSearchResponseMapper.flightSearchResponse(eq(cheapestFlightChains))).thenReturn(response);

        var sourceCityRequest = new FlightSearchCity();
        sourceCityRequest.setId("sourceCity");

        var destinationCityRequest = new FlightSearchCity();
        destinationCityRequest.setId("destinationCity");

        var request = new FlightSearchRequest();
        request.setSource(sourceCityRequest);
        request.setDestination(destinationCityRequest);

        assertThat(flightService.searchCheapestRoute(request)).isEqualTo(response);
    }
}
