package com.aaron.challenge.flightadvisor.routes.web;

import com.aaron.challenge.flightadvisor.airlines.Airline;
import com.aaron.challenge.flightadvisor.airlines.AirlineService;
import com.aaron.challenge.flightadvisor.airlines.web.AirlineResponse;
import com.aaron.challenge.flightadvisor.airports.Airport;
import com.aaron.challenge.flightadvisor.airports.AirportService;
import com.aaron.challenge.flightadvisor.airports.web.AirportResponse;
import com.aaron.challenge.flightadvisor.config.web.GenericRestMapper;
import com.aaron.challenge.flightadvisor.routes.Route;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RequiredArgsConstructor

@Component
public class RouteMapper implements GenericRestMapper<Route, RouteCreate, RouteUpdate, RouteSearch, RouteResponse> {

    private final AirlineService airlineService;

    private final AirportService airportService;

    @Override
    public Route postToEntity(RouteCreate routeCreate) {
        var sourceAirport = findAirportOrThrow(routeCreate.getSourceAirportId());
        var destinationAirport = findAirportOrThrow(routeCreate.getDestinationAirportId());

        return Route.builder()
                .id(UUID.randomUUID().toString())
                .airlineCode(routeCreate.getAirlineCode())
                .airline(findAirlineOrThrow(routeCreate.getAirlineId()))
                .sourceAirportCode(routeCreate.getSourceAirportCode())
                .sourceAirport(sourceAirport)
                .destinationAirportCode(routeCreate.getDestinationAirportCode())
                .destinationAirport(destinationAirport)
                .codeShare(routeCreate.getCodeShare())
                .stops(routeCreate.getStops())
                .equipment(routeCreate.getEquipment())
                .price(routeCreate.getPrice())
                .sourceCityMapping(sourceAirport.getCity().getMapping())
                .destinationCityMapping(destinationAirport.getCity().getMapping())
                .build();
    }

    @Override
    public Route putToEntity(RouteCreate routeCreate, Route target) {
        var sourceAirport = findAirportOrThrow(routeCreate.getSourceAirportId());
        var destinationAirport = findAirportOrThrow(routeCreate.getDestinationAirportId());

        target.setAirlineCode(routeCreate.getAirlineCode());
        target.setAirline(findAirlineOrThrow(routeCreate.getAirlineId()));
        target.setSourceAirportCode(routeCreate.getSourceAirportCode());
        target.setSourceAirport(sourceAirport);
        target.setDestinationAirportCode(routeCreate.getDestinationAirportCode());
        target.setDestinationAirport(destinationAirport);
        target.setCodeShare(routeCreate.getCodeShare());
        target.setStops(routeCreate.getStops());
        target.setEquipment(routeCreate.getEquipment());
        target.setPrice(routeCreate.getPrice());
        target.setSourceCityMapping(sourceAirport.getCity().getMapping());
        target.setDestinationCityMapping(destinationAirport.getCity().getMapping());

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
                .airline(AirlineResponse.builder()
                        .id(route.getAirline().getId())
                        .build()
                )
                .sourceAirportCode(route.getSourceAirportCode())
                .sourceAirport(
                        AirportResponse.builder()
                                .id(route.getSourceAirport().getId())
                                .build()
                )
                .destinationAirportCode(route.getDestinationAirportCode())
                .destinationAirport(
                        AirportResponse.builder()
                                .id(route.getDestinationAirport().getId())
                                .build()
                )
                .codeShare(route.getCodeShare())
                .stops(route.getStops())
                .equipment(route.getEquipment())
                .price(route.getPrice())
                .build();
    }

    private Airline findAirlineOrThrow(String id) {
        return airlineService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Airline with id " + id + " not found."));
    }

    private Airport findAirportOrThrow(String id) {
        return airportService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Airport with id " + id + " not found."));
    }
}
