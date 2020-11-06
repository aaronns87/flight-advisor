package com.aaron.challenge.flightadvisor.flights.web.response;

import com.aaron.challenge.flightadvisor.airports.Airport;
import com.aaron.challenge.flightadvisor.routes.Route;
import com.aaron.challenge.flightadvisor.routes.RouteService;
import com.aaron.challenge.flightadvisor.routes.cheapest.CheapestFlightChain;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor

@Component
public class FlightSearchResponseMapper {

    private final RouteService routeService;

    public FlightSearchResponse flightSearchResponse(List<CheapestFlightChain> cheapestFlightChains) {
        return FlightSearchResponse.builder()
                .chains(
                        flightChains(cheapestFlightChains)
                )
                .found(cheapestFlightChains.size())
                .build();
    }

    private List<FlightChain> flightChains(List<CheapestFlightChain> cheapestFlightChains) {
        return cheapestFlightChains.stream()
                .map(this::flightChain)
                .collect(Collectors.toList());
    }

    private FlightChain flightChain(CheapestFlightChain cheapestFlightChain) {
        return FlightChain.builder()
                .stops(
                        flightStops(cheapestFlightChain.getRouteIds())
                )
                .totalPrice(cheapestFlightChain.getTotalPrice())
                .hops(cheapestFlightChain.getHops())
                .build();
    }

    private List<FlightStop> flightStops(List<String> routeIds) {
        return routeIds.stream()
                .map(this::findRouteById)
                .map(this::flightStop)
                .collect(Collectors.toList());
    }

    private FlightStop flightStop(Route route) {
        return FlightStop.builder()
                .embark(
                        stopPoint(route.getSourceAirport(), route.getSourceAirportCode())
                )
                .disembark(
                        stopPoint(route.getDestinationAirport(), route.getDestinationAirportCode())
                )
                .airlineCode(route.getAirlineCode())
                .equipment(route.getEquipment())
                .build();
    }

    private StopPoint stopPoint(Airport airport, String airportCode) {
        return StopPoint.builder()
                .city(airport.getCity().getName())
                .country(airport.getCity().getCountry())
                .airport(airport.getName())
                .airportCode(airportCode)
                .build();
    }

    private Route findRouteById(String id) {
        return routeService.findById(id)
                .orElseThrow(() -> {
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to find route with id " + id + " which is part of route chain.");
                });
    }
}
