package com.aaron.challenge.flightadvisor.airlines.web;

import com.aaron.challenge.flightadvisor.airlines.Airline;
import com.aaron.challenge.flightadvisor.config.web.GenericRestMapper;
import org.springframework.stereotype.Component;

@Component
public class AirlineMapper implements GenericRestMapper<Airline, AirlineCreate, AirlineUpdate, AirlineSearch, AirlineResponse> {

    @Override
    public Airline postToEntity(AirlineCreate airlineCreate) {
        return Airline.builder()
                .code(airlineCreate.getCode())
                .build();
    }

    @Override
    public Airline putToEntity(AirlineCreate airlineCreate, Airline target) {
        target.setCode(airlineCreate.getCode());

        return target;
    }

    @Override
    public Airline patchToEntity(AirlineUpdate airlineUpdate, Airline target) {
        setIfNotNull(airlineUpdate::getCode, target::setCode);

        return target;
    }

    @Override
    public Airline searchToEntity(AirlineSearch airlineSearch) {
        return Airline.builder()
                .code(airlineSearch.getCode())
                .build();
    }

    @Override
    public AirlineResponse entityToResponse(Airline airline) {
        return AirlineResponse.builder()
                .id(airline.getId())
                .code(airline.getCode())
                .build();
    }
}
