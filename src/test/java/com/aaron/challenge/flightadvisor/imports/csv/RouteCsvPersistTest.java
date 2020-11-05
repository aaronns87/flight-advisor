package com.aaron.challenge.flightadvisor.imports.csv;

import com.aaron.challenge.flightadvisor.airlines.Airline;
import com.aaron.challenge.flightadvisor.airlines.AirlineService;
import com.aaron.challenge.flightadvisor.airports.Airport;
import com.aaron.challenge.flightadvisor.airports.AirportService;
import com.aaron.challenge.flightadvisor.routes.Route;
import com.aaron.challenge.flightadvisor.routes.RouteService;
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
public class RouteCsvPersistTest {

    @InjectMocks
    private RouteCsvPersist routeCsvPersist;

    @Mock
    private AirportService airportService;

    @Mock
    private RouteService routeService;

    @Mock
    private RouteCsvMapper routeCsvMapper;

    @Mock
    private AirlineService airlineService;

    @Mock
    private AirlineCsvMapper airlineCsvMapper;

    @Test
    public void store_routeCsv_airlineFoundByExternalId() {
        var routeCsv = RouteCsv.builder()
                .sourceAirportId(1L)
                .destinationAirportId(2L)
                .airlineCsv(AirlineCsv.builder()
                        .externalId(1L)
                        .build())
                .build();

        when(routeCsvMapper.toRoute(any(), any(), any(), any())).thenReturn(Route.builder().build());
        when(airportService.findByExternalId(any())).thenReturn(Optional.of(Airport.builder().build()));
        when(airlineCsvMapper.toAirline(any())).thenReturn(Airline.builder().build());
        when(airlineService.create(any())).thenReturn(Airline.builder().build());

        routeCsvPersist.store(routeCsv);

        verify(routeService).create(any());
    }

    @Test
    public void store_routeCsv_airlineFoundByCode() {
        var routeCsv = RouteCsv.builder()
                .sourceAirportId(1L)
                .destinationAirportId(2L)
                .airlineCsv(AirlineCsv.builder()
                        .code("code")
                        .build())
                .build();

        when(routeCsvMapper.toRoute(any(), any(), any(), any())).thenReturn(Route.builder().build());
        when(airportService.findByExternalId(any())).thenReturn(Optional.of(Airport.builder().build()));
        when(airlineCsvMapper.toAirline(any())).thenReturn(Airline.builder().build());
        when(airlineService.create(any())).thenReturn(Airline.builder().build());

        routeCsvPersist.store(routeCsv);

        verify(routeService).create(any());
    }


}
