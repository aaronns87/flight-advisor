package com.aaron.challenge.flightadvisor.routes.web;

import com.aaron.challenge.flightadvisor.airlines.Airline;
import com.aaron.challenge.flightadvisor.airlines.AirlineService;
import com.aaron.challenge.flightadvisor.airports.Airport;
import com.aaron.challenge.flightadvisor.airports.AirportService;
import com.aaron.challenge.flightadvisor.config.web.GenericRestMapper;
import com.aaron.challenge.flightadvisor.routes.Route;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor

@Component
public class RouteMapper implements GenericRestMapper<Route, RouteCreate, RouteUpdate, RouteSearch, RouteResponse> {

    private final AirlineService airlineService;

    private final AirportService airportService;

    @Override
    public Route postToEntity(RouteCreate routeCreate) {
        return Route.builder()
                .airlineCode(routeCreate.getAirlineCode())
                .airline(findAirlineOrThrow(routeCreate.getAirlineId()))
                .sourceAirportCode(routeCreate.getSourceAirportCode())
                .sourceAirport(findAirportOrThrow(routeCreate.getSourceAirportId()))
                .destinationAirportCode(routeCreate.getDestinationAirportCode())
                .destinationAirport(findAirportOrThrow(routeCreate.getDestinationAirportId()))
                .codeShare(routeCreate.getCodeShare())
                .stops(routeCreate.getStops())
                .equipment(routeCreate.getEquipment())
                .price(routeCreate.getPrice())
                .build();
    }

    @Override
    public Route putToEntity(RouteCreate routeCreate, Route target) {
        target.setAirlineCode(routeCreate.getAirlineCode());
        target.setAirline(findAirlineOrThrow(routeCreate.getAirlineId()));
        target.setSourceAirportCode(routeCreate.getSourceAirportCode());
        target.setSourceAirport(findAirportOrThrow(routeCreate.getSourceAirportId()));
        target.setDestinationAirportCode(routeCreate.getDestinationAirportCode());
        target.setDestinationAirport(findAirportOrThrow(routeCreate.getDestinationAirportId()));
        target.setCodeShare(routeCreate.getCodeShare());
        target.setStops(routeCreate.getStops());
        target.setEquipment(routeCreate.getEquipment());
        target.setPrice(routeCreate.getPrice());

        return target;
    }

    @Override
    public Route patchToEntity(RouteUpdate routeUpdate, Route target) {
        setIfNotNull(routeUpdate::getAirlineCode, target::setAirlineCode);
        setIfNotNull(routeUpdate::getSourceAirportCode, target::setSourceAirportCode);
        setIfNotNull(routeUpdate::getDestinationAirportCode, target::setDestinationAirportCode);
        setIfNotNull(routeUpdate::getCodeShare, target::setCodeShare);
        setIfNotNull(routeUpdate::getStops, target::setStops);
        setIfNotNull(routeUpdate::getEquipment, target::setEquipment);
        setIfNotNull(routeUpdate::getPrice, target::setPrice);

        return target;
    }

    @Override
    public Route searchToEntity(RouteSearch routeSearch) {
        return Route.builder()
                .airlineCode(routeSearch.getAirlineCode())
                .sourceAirportCode(routeSearch.getSourceAirportCode())
                .destinationAirportCode(routeSearch.getDestinationAirportCode())
                .codeShare(routeSearch.getCodeShare())
                .stops(routeSearch.getStops())
                .equipment(routeSearch.getEquipment())
                .price(routeSearch.getPrice())
                .build();
    }

    @Override
    public RouteResponse entityToResponse(Route route) {
        return RouteResponse.builder()
                .id(route.getId())
                .airlineCode(route.getAirlineCode())
                .airline(route.getAirline())
                .sourceAirportCode(route.getSourceAirportCode())
                .sourceAirport(route.getSourceAirport())
                .destinationAirportCode(route.getDestinationAirportCode())
                .destinationAirport(route.getDestinationAirport())
                .codeShare(route.getCodeShare())
                .stops(route.getStops())
                .equipment(route.getEquipment())
                .price(route.getPrice())
                .build();
    }

    private Airline findAirlineOrThrow(Long id) {
        return airlineService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Airline with id " + id + " not found."));
    }

    private Airport findAirportOrThrow(Long id) {
        return airportService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Airport with id " + id + " not found."));
    }
}
