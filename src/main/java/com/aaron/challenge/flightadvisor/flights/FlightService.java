package com.aaron.challenge.flightadvisor.flights;

import com.aaron.challenge.flightadvisor.cities.City;
import com.aaron.challenge.flightadvisor.cities.CityService;
import com.aaron.challenge.flightadvisor.flights.web.request.FlightSearchCity;
import com.aaron.challenge.flightadvisor.flights.web.request.FlightSearchRequest;
import com.aaron.challenge.flightadvisor.flights.web.response.FlightSearchResponse;
import com.aaron.challenge.flightadvisor.flights.web.response.FlightSearchResponseMapper;
import com.aaron.challenge.flightadvisor.routes.cheapest.CheapestFlightChain;
import com.aaron.challenge.flightadvisor.routes.cheapest.CheapestFlightsService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor

@Service
public class FlightService {

    private final CheapestFlightsService cheapestFlightsService;

    private final CityService cityService;

    private final FlightSearchResponseMapper flightSearchResponseMapper;

    public FlightSearchResponse searchCheapestRoute(FlightSearchRequest flightSearchRequest) {
        return map(
                cheapestFlightRoutes(
                        sourceCity(flightSearchRequest),
                        destinationCity(flightSearchRequest),
                        flightSearchRequest.getMaxHops(),
                        flightSearchRequest.getMaxResults()
                )
        );
    }

    private FlightSearchResponse map(List<CheapestFlightChain> cheapestFlightChains) {
        return flightSearchResponseMapper.flightSearchResponse(cheapestFlightChains);
    }

    private List<CheapestFlightChain> cheapestFlightRoutes(City sourceCity, City destinationCity, Integer maxHops, Integer maxResults) {
        return cheapestFlightsService.cheapestFlightRoutes(sourceCity, destinationCity, maxHops, maxResults);
    }

    private City sourceCity(FlightSearchRequest flightSearchRequest) {
        return findCity(
                flightSearchRequest.getSource())
                .orElseThrow(() -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Source city not found!");
                });
    }

    private City destinationCity(FlightSearchRequest flightSearchRequest) {
        return findCity(
                flightSearchRequest.getDestination())
                .orElseThrow(() -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Source city not found!");
                });
    }

    private Optional<City> findCity(FlightSearchCity flightSearchCity) {
        if (isCityIdPresent(flightSearchCity)) {
            return findCityById(
                    flightSearchCity.getId()
            );
        } else if (areCityNameAndCountryPresent(flightSearchCity)) {
            return findCityByNameAndCountry(
                    flightSearchCity.getName(),
                    flightSearchCity.getCountry()
            );
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid flight search request. " +
                    "Either city id or city name/country combination must be populated for both source and destination cities.");
        }
    }

    private boolean isCityIdPresent(FlightSearchCity flightSearchCity) {
        return StringUtils.isNotBlank(flightSearchCity.getId());
    }

    private boolean areCityNameAndCountryPresent(FlightSearchCity flightSearchCity) {
        return StringUtils.isNoneBlank(flightSearchCity.getName(), flightSearchCity.getCountry());
    }

    private Optional<City> findCityById(String id) {
        return cityService.findById(id);
    }

    private Optional<City> findCityByNameAndCountry(String name, String country) {
        return cityService.findByNameAndCountry(name, country);
    }
}
