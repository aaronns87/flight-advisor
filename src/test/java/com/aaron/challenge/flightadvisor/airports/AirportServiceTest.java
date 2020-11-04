package com.aaron.challenge.flightadvisor.airports;

import com.aaron.challenge.flightadvisor.airlines.Airline;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AirportServiceTest {

    @InjectMocks
    private AirportService airportService;

    @Mock
    private AirportRepository airportRepository;

    @Test
    public void findById() {
        var airport = new Airport();

        when(airportRepository.findById(eq(1L))).thenReturn(Optional.of(airport));

        assertThat(airportService.findById(1L)).isPresent();
    }

    @Test
    public void create() {
        var airport = new Airport();

        when(airportRepository.save(eq(airport))).thenReturn(airport);

        assertThat(airportService.create(airport)).isEqualTo(airport);
    }

    @Test
    public void update() {
        var airport = new Airport();

        when(airportRepository.save(eq(airport))).thenReturn(airport);

        assertThat(airportService.update(airport)).isEqualTo(airport);
    }

    @Test
    public void search() {
        var airport = new Airport();

        when(airportRepository.findAll(isA(Example.class), isA(Pageable.class))).thenReturn(new PageImpl(List.of(airport)));

        assertThat(airportService.search(airport, Pageable.unpaged())).isNotEmpty();
    }

    @Test
    public void findAll() {
        var airport = new Airline();

        when(airportRepository.findAll(isA(Pageable.class))).thenReturn(new PageImpl(List.of(airport)));

        assertThat(airportService.findAll(Pageable.unpaged())).isNotEmpty();
    }

    @Test
    public void delete() {
        var airport = new Airport();

        airportService.delete(airport);

        verify(airportRepository).delete(airport);
    }
}
