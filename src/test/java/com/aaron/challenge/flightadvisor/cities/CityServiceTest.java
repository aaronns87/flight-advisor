package com.aaron.challenge.flightadvisor.cities;

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
public class CityServiceTest {


    @InjectMocks
    private CityService cityService;

    @Mock
    private CityRepository cityRepository;

    @Test
    public void findById() {
        var city = new City();

        when(cityRepository.findById(eq(1L))).thenReturn(Optional.of(city));

        assertThat(cityService.findById(1L)).isPresent();
    }

    @Test
    public void findByNameAndCountry() {
        var city = new City();

        when(cityRepository.findByNameAndCountry(eq("name"), eq("country"))).thenReturn(Optional.of(city));

        assertThat(cityService.findByNameAndCountry("name", "country")).isPresent();
    }

    @Test
    public void create() {
        var city = new City();

        when(cityRepository.save(eq(city))).thenReturn(city);

        assertThat(cityService.create(city)).isEqualTo(city);
    }

    @Test
    public void update() {
        var city = new City();

        when(cityRepository.save(eq(city))).thenReturn(city);

        assertThat(cityService.update(city)).isEqualTo(city);
    }

    @Test
    public void search() {
        var city = new City();

        when(cityRepository.findAll(isA(Example.class), isA(Pageable.class))).thenReturn(new PageImpl(List.of(city)));

        assertThat(cityService.search(city, Pageable.unpaged())).isNotEmpty();
    }

    @Test
    public void findAll() {
        var city = new City();

        when(cityRepository.findAll(isA(Pageable.class))).thenReturn(new PageImpl(List.of(city)));

        assertThat(cityService.findAll(Pageable.unpaged())).isNotEmpty();
    }

    @Test
    public void delete() {
        var city = new City();

        cityService.delete(city);

        verify(cityRepository).delete(city);
    }
}
