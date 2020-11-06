package com.aaron.challenge.flightadvisor.routes;

import com.aaron.challenge.flightadvisor.routes.cheapest.CheapestFlightChain;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor

@Service
public class RouteService {

    public static final ExampleMatcher matcher = ExampleMatcher
            .matching()
            .withIgnorePaths("id")
            .withIgnoreNullValues()
            .withIgnoreCase(true)
            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

    private final RouteRepository routeRepository;

    public Optional<Route> findById(String id) {
        return routeRepository.findById(id);
    }

    public Route create(Route route) {
        return routeRepository.save(route);
    }

    public Route update(Route route) {
        return routeRepository.save(route);
    }

    public Page<Route> search(Route route, Pageable pageable) {
        return routeRepository.findAll(Example.of(route, matcher), pageable);
    }

    public Page<Route> findAll(Pageable pageable) {
        return routeRepository.findAll(pageable);
    }

    public void delete(Route route) {
        routeRepository.delete(route);
    }

    public List<CheapestFlightChain> findCheapestFlightChain(Integer sourceCityMapping, Integer destinationCityMapping, Integer maxHops, Integer maxResults) {
        return routeRepository.findCheapestFlightChain(sourceCityMapping, destinationCityMapping, maxHops, maxResults);
    }
}
