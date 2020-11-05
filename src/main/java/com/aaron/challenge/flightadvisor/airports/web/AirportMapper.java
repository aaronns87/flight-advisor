package com.aaron.challenge.flightadvisor.airports.web;

import com.aaron.challenge.flightadvisor.airports.Airport;
import com.aaron.challenge.flightadvisor.cities.City;
import com.aaron.challenge.flightadvisor.cities.CityService;
import com.aaron.challenge.flightadvisor.cities.web.CityResponse;
import com.aaron.challenge.flightadvisor.config.web.GenericRestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RequiredArgsConstructor

@Component
public class AirportMapper implements GenericRestMapper<Airport, AirportCreate, AirportUpdate, AirportSearch, AirportResponse> {

    private final CityService cityService;

    @Override
    public Airport postToEntity(AirportCreate airportCreate) {
        return Airport.builder()
                .id(UUID.randomUUID().toString())
                .name(airportCreate.getName())
                .city(findCityOrThrow(airportCreate.getCityId()))
                .iata(airportCreate.getIata())
                .icao(airportCreate.getIcao())
                .latitude(airportCreate.getLatitude())
                .longitude(airportCreate.getLongitude())
                .altitude(airportCreate.getAltitude())
                .timeZone(airportCreate.getTimeZone())
                .dst(airportCreate.getDst())
                .tz(airportCreate.getTz())
                .type(airportCreate.getType())
                .source(airportCreate.getSource())
                .build();
    }

    @Override
    public Airport putToEntity(AirportCreate airportCreate, Airport target) {
        target.setName(airportCreate.getName());
        target.setCity(findCityOrThrow(airportCreate.getCityId()));
        target.setIata(airportCreate.getIata());
        target.setIcao(airportCreate.getIcao());
        target.setLatitude(airportCreate.getLatitude());
        target.setLongitude(airportCreate.getLongitude());
        target.setAltitude(airportCreate.getAltitude());
        target.setTimeZone(airportCreate.getTimeZone());
        target.setDst(airportCreate.getDst());
        target.setTz(airportCreate.getTz());
        target.setType(airportCreate.getType());
        target.setSource(airportCreate.getSource());

        return target;
    }

    @Override
    public Airport patchToEntity(AirportUpdate airportUpdate, Airport target) {
        setIfNotNull(airportUpdate::getName, target::setName);
        setIfNotNull(airportUpdate::getIata, target::setIata);
        setIfNotNull(airportUpdate::getIcao, target::setIcao);
        setIfNotNull(airportUpdate::getLatitude, target::setLatitude);
        setIfNotNull(airportUpdate::getLongitude, target::setLongitude);
        setIfNotNull(airportUpdate::getAltitude, target::setAltitude);
        setIfNotNull(airportUpdate::getTimeZone, target::setTimeZone);
        setIfNotNull(airportUpdate::getDst, target::setDst);
        setIfNotNull(airportUpdate::getTz, target::setTz);
        setIfNotNull(airportUpdate::getType, target::setType);
        setIfNotNull(airportUpdate::getSource, target::setSource);

        return target;
    }

    @Override
    public Airport searchToEntity(AirportSearch airportSearch) {
        return Airport.builder()
                .name(airportSearch.getName())
                .iata(airportSearch.getIata())
                .icao(airportSearch.getIcao())
                .latitude(airportSearch.getLatitude())
                .longitude(airportSearch.getLongitude())
                .altitude(airportSearch.getAltitude())
                .timeZone(airportSearch.getTimeZone())
                .dst(airportSearch.getDst())
                .tz(airportSearch.getTz())
                .type(airportSearch.getType())
                .source(airportSearch.getSource())
                .build();
    }

    @Override
    public AirportResponse entityToResponse(Airport airport) {
        return AirportResponse.builder()
                .id(airport.getId())
                .externalId(airport.getExternalId())
                .name(airport.getName())
                .city(CityResponse.builder()
                        .id(airport.getCity().getId())
                        .name(airport.getCity().getName())
                        .country(airport.getCity().getCountry())
                        .build()
                )
                .iata(airport.getIata())
                .icao(airport.getIcao())
                .latitude(airport.getLatitude())
                .longitude(airport.getLongitude())
                .altitude(airport.getAltitude())
                .timeZone(airport.getTimeZone())
                .dst(airport.getDst())
                .tz(airport.getTz())
                .type(airport.getType())
                .source(airport.getSource())
                .build();
    }

    private City findCityOrThrow(String id) {
        return cityService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "City with id " + id + " not found."));
    }
}
