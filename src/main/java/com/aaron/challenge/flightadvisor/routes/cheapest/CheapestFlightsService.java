package com.aaron.challenge.flightadvisor.routes.cheapest;

import com.aaron.challenge.flightadvisor.cities.City;
import com.aaron.challenge.flightadvisor.routes.RouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor

@Slf4j
@Service
public class CheapestFlightsService {

    private final RouteService routeService;

    @Value("${flight.search.defaultMaxHops:3}")
    private Integer defaultMaxHops;

    @Value("${flight.search.defaultMaxResults:5}")
    private Integer defaultMaxResults;

    public List<CheapestFlightChain> cheapestFlightRoutes(City sourceCity, City destinationCity, Integer maxHops, Integer maxResults) {
        return cheapestFlightChains(
                sourceCity.getMapping(),
                destinationCity.getMapping(),
                maxHopsOrDefault(maxHops),
                maxResultsOrDefault(maxResults)
        );
    }

    private List<CheapestFlightChain> cheapestFlightChains(Integer sourceCityMapping, Integer destinationCityMapping, Integer maxHops, Integer maxResults) {
        return routeService.findCheapestFlightChain(
                sourceCityMapping,
                destinationCityMapping,
                maxHops,
                maxResults
        );
    }

    private Integer maxHopsOrDefault(Integer maxHops) {
        return Objects.nonNull(maxHops) ? maxHops : defaultMaxHops;
    }

    private Integer maxResultsOrDefault(Integer maxResults) {
        return Objects.nonNull(maxResults) ? maxResults : defaultMaxResults;
    }
}
