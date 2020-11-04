package com.aaron.challenge.flightadvisor.routes;

import com.aaron.challenge.flightadvisor.comments.Comment;
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
public class RouteServiceTest {

    @InjectMocks
    private RouteService routeService;

    @Mock
    private RouteRepository routeRepository;

    @Test
    public void findById() {
        var route = new Route();

        when(routeRepository.findById(eq("id"))).thenReturn(Optional.of(route));

        assertThat(routeService.findById("id")).isPresent();
    }

    @Test
    public void create() {
        var route = new Route();

        when(routeRepository.save(eq(route))).thenReturn(route);

        assertThat(routeService.create(route)).isEqualTo(route);
    }

    @Test
    public void update() {
        var route = new Route();

        when(routeRepository.save(eq(route))).thenReturn(route);

        assertThat(routeService.update(route)).isEqualTo(route);
    }

    @Test
    public void search() {
        var route = new Route();

        when(routeRepository.findAll(isA(Example.class), isA(Pageable.class))).thenReturn(new PageImpl(List.of(route)));

        assertThat(routeService.search(route, Pageable.unpaged())).isNotEmpty();
    }

    @Test
    public void findAll() {
        var route = new Route();

        when(routeRepository.findAll(isA(Pageable.class))).thenReturn(new PageImpl(List.of(route)));

        assertThat(routeService.findAll(Pageable.unpaged())).isNotEmpty();
    }

    @Test
    public void delete() {
        var route = new Route();

        routeService.delete(route);

        verify(routeRepository).delete(route);
    }
}
