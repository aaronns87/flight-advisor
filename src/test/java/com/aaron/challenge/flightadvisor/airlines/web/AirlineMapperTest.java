package com.aaron.challenge.flightadvisor.airlines.web;

import com.aaron.challenge.flightadvisor.airlines.Airline;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class AirlineMapperTest {

    @InjectMocks
    private AirlineMapper airlineMapper;

    @Test
    public void postToEntity_airlineCreate_airline() {
        var airlineCreate = new AirlineCreate();
        airlineCreate.setCode("code");

        var airline = airlineMapper.postToEntity(airlineCreate);

        assertThat(airline.getCode()).isEqualTo("code");
    }

    @Test
    public void putToEntity_airlineCreate_target_airline() {
        var airlineCreate = new AirlineCreate();
        airlineCreate.setCode("code");

        var target = new Airline();

        var airline = airlineMapper.putToEntity(airlineCreate, target);

        assertThat(airline.getCode()).isEqualTo("code");
    }

    @Test
    public void patchToEntity_airlineUpdate_target_airline() {
        var airlineUpdate = new AirlineUpdate();
        airlineUpdate.setCode("code");

        var target = new Airline();

        var airline = airlineMapper.patchToEntity(airlineUpdate, target);

        assertThat(airline.getCode()).isEqualTo("code");
    }

    @Test
    public void searchToEntity_airlineSearch_airline() {
        var airlineSearch = new AirlineSearch();
        airlineSearch.setCode("code");

        var airline = airlineMapper.searchToEntity(airlineSearch);

        assertThat(airline.getCode()).isEqualTo("code");
    }

    @Test
    public void entityToResponse_airline_airlineResponse() {
        var airline = new Airline();
        airline.setCode("code");

        var airlineResponse = airlineMapper.entityToResponse(airline);

        assertThat(airlineResponse.getCode()).isEqualTo("code");
    }
}
