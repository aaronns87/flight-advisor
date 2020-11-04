package com.aaron.challenge.flightadvisor.airports;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor

@Service
public class AirportService {

    public static final ExampleMatcher matcher = ExampleMatcher
            .matching()
            .withIgnorePaths("id")
            .withIgnoreNullValues()
            .withIgnoreCase(true)
            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

    private final AirportRepository airportRepository;

    public Optional<Airport> findById(String id) {
        return airportRepository.findById(id);
    }

    public Optional<Airport> findByExternalId(Long externalId) {
        return airportRepository.findByExternalId(externalId);
    }

    public Airport create(Airport airport) {
        return airportRepository.save(airport);
    }

    public Airport update(Airport airport) {
        return airportRepository.save(airport);
    }

    public Page<Airport> search(Airport airport, Pageable pageable) {
        return airportRepository.findAll(Example.of(airport, matcher), pageable);
    }

    public Page<Airport> findAll(Pageable pageable) {
        return airportRepository.findAll(pageable);
    }

    public void delete(Airport airport) {
        airportRepository.delete(airport);
    }
}
