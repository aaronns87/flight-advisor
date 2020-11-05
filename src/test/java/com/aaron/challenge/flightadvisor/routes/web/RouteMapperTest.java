package com.aaron.challenge.flightadvisor.routes.web;

import com.aaron.challenge.flightadvisor.airlines.Airline;
import com.aaron.challenge.flightadvisor.airlines.AirlineService;
import com.aaron.challenge.flightadvisor.airports.Airport;
import com.aaron.challenge.flightadvisor.airports.AirportService;
import com.aaron.challenge.flightadvisor.routes.Route;
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
public class RouteMapperTest {

    @InjectMocks
    private RouteMapper routeMapper;

    @Mock
    private AirlineService airlineService;

    @Mock
    private AirportService airportService;

    @Test
    public void postToEntity_airportCreate_airport() {
        var airline = new Airline();

        when(airlineService.findById(eq("airlineId"))).thenReturn(Optional.of(airline));

        var source = new Airport();
        var destination = new Airport();

        when(airportService.findById(eq("sourceAirportId"))).thenReturn(Optional.of(source));
        when(airportService.findById(eq("destinationAirportId"))).thenReturn(Optional.of(destination));


        var routeCreate = new RouteCreate();
        routeCreate.setAirlineCode("airlineCode");
        routeCreate.setAirlineId("airlineId");
        routeCreate.setSourceAirportCode("sourceAirportCode");
        routeCreate.setSourceAirportId("sourceAirportId");
        routeCreate.setDestinationAirportCode("destinationAirportCode");
        routeCreate.setDestinationAirportId("destinationAirportId");
        routeCreate.setStops(1);
        routeCreate.setPrice(10.0f);
        routeCreate.setEquipment("equipment");
        routeCreate.setCodeShare(true);

        var route = routeMapper.postToEntity(routeCreate);

        assertThat(route.getId()).isNotBlank();
        assertThat(route.getAirlineCode()).isEqualTo("airlineCode");
        assertThat(route.getAirline()).isEqualTo(airline);
        assertThat(route.getSourceAirportCode()).isEqualTo("sourceAirportCode");
        assertThat(route.getSourceAirport()).isEqualTo(source);
        assertThat(route.getDestinationAirportCode()).isEqualTo("destinationAirportCode");
        assertThat(route.getDestinationAirport()).isEqualTo(destination);
        assertThat(route.getStops()).isEqualTo(1);
        assertThat(route.getPrice()).isEqualTo(10.0f);
        assertThat(route.getEquipment()).isEqualTo("equipment");
        assertThat(route.getCodeShare()).isTrue();
    }

    @Test
    public void putToEntity_airportCreate_target_airport() {
        var airline = new Airline();

        when(airlineService.findById(eq("airlineId"))).thenReturn(Optional.of(airline));

        var source = new Airport();
        var destination = new Airport();

        when(airportService.findById(eq("sourceAirportId"))).thenReturn(Optional.of(source));
        when(airportService.findById(eq("destinationAirportId"))).thenReturn(Optional.of(destination));

        var routeCreate = new RouteCreate();
        routeCreate.setAirlineCode("airlineCode");
        routeCreate.setAirlineId("airlineId");
        routeCreate.setSourceAirportCode("sourceAirportCode");
        routeCreate.setSourceAirportId("sourceAirportId");
        routeCreate.setDestinationAirportCode("destinationAirportCode");
        routeCreate.setDestinationAirportId("destinationAirportId");
        routeCreate.setStops(1);
        routeCreate.setPrice(10.0f);
        routeCreate.setEquipment("equipment");
        routeCreate.setCodeShare(true);

        var target = new Route();

        var route = routeMapper.putToEntity(routeCreate, target);

        assertThat(route.getAirlineCode()).isEqualTo("airlineCode");
        assertThat(route.getAirline()).isEqualTo(airline);
        assertThat(route.getSourceAirportCode()).isEqualTo("sourceAirportCode");
        assertThat(route.getSourceAirport()).isEqualTo(source);
        assertThat(route.getDestinationAirportCode()).isEqualTo("destinationAirportCode");
        assertThat(route.getDestinationAirport()).isEqualTo(destination);
        assertThat(route.getStops()).isEqualTo(1);
        assertThat(route.getPrice()).isEqualTo(10.0f);
        assertThat(route.getEquipment()).isEqualTo("equipment");
        assertThat(route.getCodeShare()).isTrue();
    }

    @Test
    public void patchToEntity_airportUpdate_target_airport() {
        var routeUpdate = new RouteUpdate();
        routeUpdate.setAirlineCode("airlineCode");
        routeUpdate.setSourceAirportCode("sourceAirportCode");
        routeUpdate.setDestinationAirportCode("destinationAirportCode");
        routeUpdate.setStops(1);
        routeUpdate.setPrice(10.0f);
        routeUpdate.setEquipment("equipment");
        routeUpdate.setCodeShare(true);

        var target = new Route();

        var route = routeMapper.patchToEntity(routeUpdate, target);

        assertThat(route.getAirlineCode()).isEqualTo("airlineCode");
        assertThat(route.getSourceAirportCode()).isEqualTo("sourceAirportCode");
        assertThat(route.getDestinationAirportCode()).isEqualTo("destinationAirportCode");
        assertThat(route.getStops()).isEqualTo(1);
        assertThat(route.getPrice()).isEqualTo(10.0f);
        assertThat(route.getCodeShare()).isTrue();
    }

    @Test
    public void searchToEntity_airportSearch_airport() {
        var routeSearch = new RouteSearch();
        routeSearch.setAirlineCode("airlineCode");
        routeSearch.setSourceAirportCode("sourceAirportCode");
        routeSearch.setDestinationAirportCode("destinationAirportCode");
        routeSearch.setStops(1);
        routeSearch.setPrice(10.0f);
        routeSearch.setEquipment("equipment");
        routeSearch.setCodeShare(true);

        var route = routeMapper.searchToEntity(routeSearch);

        assertThat(route.getAirlineCode()).isEqualTo("airlineCode");
        assertThat(route.getSourceAirportCode()).isEqualTo("sourceAirportCode");
        assertThat(route.getDestinationAirportCode()).isEqualTo("destinationAirportCode");
        assertThat(route.getStops()).isEqualTo(1);
        assertThat(route.getPrice()).isEqualTo(10.0f);
        assertThat(route.getEquipment()).isEqualTo("equipment");
        assertThat(route.getCodeShare()).isTrue();
    }

    @Test
    public void entityToResponse_airport_airportResponse() {
        var airline = new Airline();
        airline.setId("airlineId");

        var sourceAirport = new Airport();
        sourceAirport.setId("sourceAirportId");

        var destinationAirport = new Airport();
        destinationAirport.setId("destinationAirportId");

        var route = new Route();
        route.setId("id");
        route.setAirlineCode("airlineCode");
        route.setAirline(airline);
        route.setSourceAirportCode("sourceAirportCode");
        route.setSourceAirport(sourceAirport);
        route.setDestinationAirportCode("destinationAirportCode");
        route.setDestinationAirport(destinationAirport);
        route.setStops(1);
        route.setPrice(10.0f);
        route.setEquipment("equipment");
        route.setCodeShare(true);

        var routeResponse = routeMapper.entityToResponse(route);

        assertThat(routeResponse.getId()).isEqualTo("id");
        assertThat(routeResponse.getAirlineCode()).isEqualTo("airlineCode");
        assertThat(routeResponse.getAirline().getId()).isEqualTo("airlineId");
        assertThat(routeResponse.getSourceAirportCode()).isEqualTo("sourceAirportCode");
        assertThat(routeResponse.getSourceAirport().getId()).isEqualTo("sourceAirportId");
        assertThat(routeResponse.getDestinationAirportCode()).isEqualTo("destinationAirportCode");
        assertThat(routeResponse.getDestinationAirport().getId()).isEqualTo("destinationAirportId");
        assertThat(routeResponse.getStops()).isEqualTo(1);
        assertThat(routeResponse.getPrice()).isEqualTo(10.0f);
        assertThat(route.getEquipment()).isEqualTo("equipment");
        assertThat(routeResponse.getCodeShare()).isTrue();
    }
}
