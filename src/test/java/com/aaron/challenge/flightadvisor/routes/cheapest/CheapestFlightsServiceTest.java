package com.aaron.challenge.flightadvisor.routes.cheapest;

import com.aaron.challenge.flightadvisor.cities.City;
import com.aaron.challenge.flightadvisor.routes.RouteService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CheapestFlightsServiceTest {

    @InjectMocks
    private CheapestFlightsService cheapestFlightsService;

    @Mock
    private RouteService routeService;

    @Before
    public void beforeEach() {
        ReflectionTestUtils.setField(cheapestFlightsService, "defaultMaxHops", 5);
        ReflectionTestUtils.setField(cheapestFlightsService, "defaultMaxResults", 10);
    }

    @Test
    public void cheapestFlightRoutes_sourceCity_destinationCity_maxHops_maxResults() {
        var sourceCity = City.builder()
                .mapping(1)
                .build();
        var destinationCity = City.builder()
                .mapping(2)
                .build();

        var chains = List.of(new CheapestFlightChain());
        when(routeService.findCheapestFlightChain(eq(1), eq(2), eq(1), eq(1))).thenReturn(chains);

        var results = cheapestFlightsService.cheapestFlightRoutes(sourceCity, destinationCity, 1, 1);

        assertThat(results).isEqualTo(chains);
    }

    @Test
    public void cheapestFlightRoutes_noMaxHopsAndMaxResults_defaultValuesUsed() {
        var sourceCity = City.builder()
                .mapping(1)
                .build();
        var destinationCity = City.builder()
                .mapping(2)
                .build();

        var chains = List.of(new CheapestFlightChain());
        when(routeService.findCheapestFlightChain(eq(1), eq(2), eq(5), eq(10))).thenReturn(chains);

        var results = cheapestFlightsService.cheapestFlightRoutes(sourceCity, destinationCity, null, null);

        assertThat(results).isEqualTo(chains);
    }
}
