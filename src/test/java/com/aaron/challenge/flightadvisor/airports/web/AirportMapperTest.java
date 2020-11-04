package com.aaron.challenge.flightadvisor.airports.web;

import com.aaron.challenge.flightadvisor.airports.Airport;
import com.aaron.challenge.flightadvisor.airports.DST;
import com.aaron.challenge.flightadvisor.airports.Type;
import com.aaron.challenge.flightadvisor.cities.City;
import com.aaron.challenge.flightadvisor.cities.CityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AirportMapperTest {

    @InjectMocks
    private AirportMapper airportMapper;

    @Mock
    private CityService cityService;

    @Test
    public void postToEntity_airportCreate_airport() {
        var city = new City();

        when(cityService.findById(eq("cityId"))).thenReturn(Optional.of(city));

        var airportCreate = new AirportCreate();
        airportCreate.setAltitude(1);
        airportCreate.setLatitude("latitude");
        airportCreate.setLongitude("longitude");
        airportCreate.setCityId("cityId");
        airportCreate.setDst(DST.A);
        airportCreate.setIata("iata");
        airportCreate.setIcao("icao");
        airportCreate.setName("name");
        airportCreate.setTimeZone(1.0);
        airportCreate.setTz("tz");
        airportCreate.setType(Type.AIRPORT);
        airportCreate.setSource("source");

        var airport = airportMapper.postToEntity(airportCreate);

        assertThat(airport.getId()).isNotBlank();
        assertThat(airport.getAltitude()).isEqualTo(1);
        assertThat(airport.getLatitude()).isEqualTo("latitude");
        assertThat(airport.getLongitude()).isEqualTo("longitude");
        assertThat(airport.getCity()).isEqualTo(city);
        assertThat(airport.getDst()).isEqualTo(DST.A);
        assertThat(airport.getIata()).isEqualTo("iata");
        assertThat(airport.getIcao()).isEqualTo("icao");
        assertThat(airport.getName()).isEqualTo("name");
        assertThat(airport.getTimeZone()).isEqualTo(1);
        assertThat(airport.getTz()).isEqualTo("tz");
        assertThat(airport.getType()).isEqualTo(Type.AIRPORT);
        assertThat(airport.getSource()).isEqualTo("source");
    }

    @Test
    public void putToEntity_airportCreate_target_airport() {
        var city = new City();

        when(cityService.findById(eq("cityId"))).thenReturn(Optional.of(city));

        var airportCreate = new AirportCreate();
        airportCreate.setAltitude(1);
        airportCreate.setLatitude("latitude");
        airportCreate.setLongitude("longitude");
        airportCreate.setCityId("cityId");
        airportCreate.setDst(DST.A);
        airportCreate.setIata("iata");
        airportCreate.setIcao("icao");
        airportCreate.setName("name");
        airportCreate.setTimeZone(1.0);
        airportCreate.setTz("tz");
        airportCreate.setType(Type.AIRPORT);
        airportCreate.setSource("source");

        var target = new Airport();

        var airport = airportMapper.putToEntity(airportCreate, target);

        assertThat(airport.getAltitude()).isEqualTo(1);
        assertThat(airport.getLatitude()).isEqualTo("latitude");
        assertThat(airport.getLongitude()).isEqualTo("longitude");
        assertThat(airport.getCity()).isEqualTo(city);
        assertThat(airport.getDst()).isEqualTo(DST.A);
        assertThat(airport.getIata()).isEqualTo("iata");
        assertThat(airport.getIcao()).isEqualTo("icao");
        assertThat(airport.getName()).isEqualTo("name");
        assertThat(airport.getTimeZone()).isEqualTo(1);
        assertThat(airport.getTz()).isEqualTo("tz");
        assertThat(airport.getType()).isEqualTo(Type.AIRPORT);
        assertThat(airport.getSource()).isEqualTo("source");
    }

    @Test
    public void patchToEntity_airportUpdate_target_airport() {
        var airportUpdate = new AirportUpdate();
        airportUpdate.setAltitude(1);
        airportUpdate.setLatitude("latitude");
        airportUpdate.setLongitude("longitude");
        airportUpdate.setDst(DST.A);
        airportUpdate.setIata("iata");
        airportUpdate.setIcao("icao");
        airportUpdate.setName("name");
        airportUpdate.setTimeZone(1.0);
        airportUpdate.setTz("tz");
        airportUpdate.setType(Type.AIRPORT);
        airportUpdate.setSource("source");

        var target = new Airport();

        var airport = airportMapper.patchToEntity(airportUpdate, target);

        assertThat(airport.getAltitude()).isEqualTo(1);
        assertThat(airport.getLatitude()).isEqualTo("latitude");
        assertThat(airport.getLongitude()).isEqualTo("longitude");
        assertThat(airport.getDst()).isEqualTo(DST.A);
        assertThat(airport.getIata()).isEqualTo("iata");
        assertThat(airport.getIcao()).isEqualTo("icao");
        assertThat(airport.getName()).isEqualTo("name");
        assertThat(airport.getTimeZone()).isEqualTo(1);
        assertThat(airport.getTz()).isEqualTo("tz");
        assertThat(airport.getType()).isEqualTo(Type.AIRPORT);
        assertThat(airport.getSource()).isEqualTo("source");
    }

    @Test
    public void searchToEntity_airportSearch_airport() {
        var airportSearch = new AirportSearch();
        airportSearch.setAltitude(1);
        airportSearch.setLatitude("latitude");
        airportSearch.setLongitude("longitude");
        airportSearch.setDst(DST.A);
        airportSearch.setIata("iata");
        airportSearch.setIcao("icao");
        airportSearch.setName("name");
        airportSearch.setTimeZone(1.0);
        airportSearch.setTz("tz");
        airportSearch.setType(Type.AIRPORT);
        airportSearch.setSource("source");

        var airport = airportMapper.searchToEntity(airportSearch);

        assertThat(airport.getAltitude()).isEqualTo(1);
        assertThat(airport.getLatitude()).isEqualTo("latitude");
        assertThat(airport.getLongitude()).isEqualTo("longitude");
        assertThat(airport.getDst()).isEqualTo(DST.A);
        assertThat(airport.getIata()).isEqualTo("iata");
        assertThat(airport.getIcao()).isEqualTo("icao");
        assertThat(airport.getName()).isEqualTo("name");
        assertThat(airport.getTimeZone()).isEqualTo(1);
        assertThat(airport.getTz()).isEqualTo("tz");
        assertThat(airport.getType()).isEqualTo(Type.AIRPORT);
        assertThat(airport.getSource()).isEqualTo("source");
    }

    @Test
    public void entityToResponse_airport_airportResponse() {
        var airport = new Airport();
        airport.setId("id");
        airport.setExternalId(1L);
        airport.setAltitude(1);
        airport.setLatitude("latitude");
        airport.setLongitude("longitude");
        airport.setDst(DST.A);
        airport.setIata("iata");
        airport.setIcao("icao");
        airport.setName("name");
        airport.setTimeZone(1.0);
        airport.setTz("tz");
        airport.setType(Type.AIRPORT);
        airport.setSource("source");

        var airportResponse = airportMapper.entityToResponse(airport);

        assertThat(airportResponse.getId()).isEqualTo("id");
        assertThat(airportResponse.getExternalId()).isEqualTo(1L);
        assertThat(airportResponse.getAltitude()).isEqualTo(1);
        assertThat(airportResponse.getLatitude()).isEqualTo("latitude");
        assertThat(airportResponse.getLongitude()).isEqualTo("longitude");
        assertThat(airportResponse.getDst()).isEqualTo(DST.A);
        assertThat(airportResponse.getIata()).isEqualTo("iata");
        assertThat(airportResponse.getIcao()).isEqualTo("icao");
        assertThat(airportResponse.getName()).isEqualTo("name");
        assertThat(airportResponse.getTimeZone()).isEqualTo(1);
        assertThat(airportResponse.getTz()).isEqualTo("tz");
        assertThat(airportResponse.getType()).isEqualTo(Type.AIRPORT);
        assertThat(airportResponse.getSource()).isEqualTo("source");
    }
}
