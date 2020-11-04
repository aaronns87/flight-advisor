package com.aaron.challenge.flightadvisor.imports.csv;

import com.aaron.challenge.flightadvisor.airlines.Airline;
import com.aaron.challenge.flightadvisor.airlines.AirlineService;
import com.aaron.challenge.flightadvisor.airports.Airport;
import com.aaron.challenge.flightadvisor.airports.AirportService;
import com.aaron.challenge.flightadvisor.routes.Route;
import com.aaron.challenge.flightadvisor.routes.RouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor

@Component
public class RouteCsvPersist {

    private final AirportService airportService;

    private final RouteService routeService;
    private final RouteCsvMapper routeCsvMapper;

    private final AirlineService airlineService;
    private final AirlineCsvMapper airlineCsvMapper;

    void store(RouteCsv routeCsv) {
        try {
            createInDb(
                    map(routeCsv)
            );
        } catch (Exception e) {
            log.warn("Skipping import of route {} due to error.", routeCsv.toString(), e);
        }
    }

    Route map(RouteCsv routeCsv) {
        return routeCsvMapper.toRoute(
                routeCsv,
                findOrCreateAirline(
                        routeCsv.getAirlineCsv()
                ),
                findAirport(
                        routeCsv.getSourceAirportId()
                ),
                findAirport(
                        routeCsv.getDestinationAirportId()
                )
        );
    }

    private void createInDb(Route route) {
        routeService.create(route);
    }

    Airline findOrCreateAirline(AirlineCsv airlineCsv) {
        return findAirline(
                airlineCsv.getId()
        ).orElseGet(
                () -> create(airlineCsv)
        );
    }

    private Optional<Airline> findAirline(Long id) {
        return airlineService.findById(id);
    }

    private Airline create(AirlineCsv airlineCsv) {
        return createInDb(
                map(airlineCsv)
        );
    }

    private Airline map(AirlineCsv airlineCsv) {
        return airlineCsvMapper.toAirline(airlineCsv);
    }

    private Airline createInDb(Airline airline) {
        return airlineService.create(airline);
    }

    Airport findAirport(Long id) {
        return airportService.findById(id)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException("Airport with id " + id + " not found.");
                });
    }
}
