package com.aaron.challenge.flightadvisor.airlines;

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
public class AirlineServiceTest {

    @InjectMocks
    private AirlineService airlineService;

    @Mock
    private AirlineRepository airlineRepository;

    @Test
    public void findById() {
        var airline = new Airline();

        when(airlineRepository.findById(eq("id"))).thenReturn(Optional.of(airline));

        assertThat(airlineService.findById("id")).isPresent();
    }

    @Test
    public void findByExternalId() {
        var airline = new Airline();

        when(airlineRepository.findByExternalId(eq(1L))).thenReturn(Optional.of(airline));

        assertThat(airlineService.findByExternalId(1L)).isPresent();
    }

    @Test
    public void create() {
        var airline = new Airline();

        when(airlineRepository.save(eq(airline))).thenReturn(airline);

        assertThat(airlineService.create(airline)).isEqualTo(airline);
    }

    @Test
    public void update() {
        var airline = new Airline();

        when(airlineRepository.save(eq(airline))).thenReturn(airline);

        assertThat(airlineService.update(airline)).isEqualTo(airline);
    }

    @Test
    public void search() {
        var airline = new Airline();

        when(airlineRepository.findAll(isA(Example.class), isA(Pageable.class))).thenReturn(new PageImpl(List.of(airline)));

        assertThat(airlineService.search(airline, Pageable.unpaged())).isNotEmpty();
    }

    @Test
    public void findAll() {
        var airline = new Airline();

        when(airlineRepository.findAll(isA(Pageable.class))).thenReturn(new PageImpl(List.of(airline)));

        assertThat(airlineService.findAll(Pageable.unpaged())).isNotEmpty();
    }

    @Test
    public void delete() {
        var airline = new Airline();

        airlineService.delete(airline);

        verify(airlineRepository).delete(airline);
    }

}
