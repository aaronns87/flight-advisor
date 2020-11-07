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
import java.util.Objects;
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
        } catch (EntityNotFoundException e) {
            log.info("Skipping import of route {} due to {}.", routeCsv.toString(), e.getMessage());
        } catch (Exception e) {
            log.warn("Skipping import of route {} due to error.", routeCsv.toString(), e);
        }
    }

    private Route map(RouteCsv routeCsv) {
        return routeCsvMapper.toRoute(
                routeCsv,
                findOrCreateAirline(
                        routeCsv.getAirlineCsv()
                ),
                findAirportByExternalId(
                        routeCsv.getSourceAirportId()
                ),
                findAirportByExternalId(
                        routeCsv.getDestinationAirportId()
                )
        );
    }

    private void createInDb(Route route) {
        routeService.create(route);
    }

    private Airline findOrCreateAirline(AirlineCsv airlineCsv) {
        if (notAirlineExternalIdValid(airlineCsv)) {
            log.warn("Unable to search airline {} by invalid external id. Falling back to code {}", airlineCsv.toString(), airlineCsv.getCode());

            return findAirlineByCode(
                    airlineCsv.getCode()
            ).orElseGet(
                    () -> create(airlineCsv)
            );
        } else {
            return findAirlineByExternalId(
                    airlineCsv.getExternalId()
            ).orElseGet(
                    () -> create(airlineCsv)
            );
        }
    }

    private Optional<Airline> findAirlineByExternalId(Long externalId) {
        return airlineService.findByExternalId(externalId);
    }

    private Optional<Airline> findAirlineByCode(String code) {
        return airlineService.findByCode(code);
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

    private Airport findAirportByExternalId(Long externalId) {
        return airportService.findByExternalId(externalId)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException("Airport with external id " + externalId + " not found. Unable to create route. Breaking failure.");
                });
    }

    private boolean notAirlineExternalIdValid(AirlineCsv airlineCsv) {
        return Objects.isNull(airlineCsv.getExternalId());
    }
}
