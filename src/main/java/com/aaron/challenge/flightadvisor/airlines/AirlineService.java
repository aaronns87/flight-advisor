package com.aaron.challenge.flightadvisor.airlines;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor

@Service
public class AirlineService {

    public static final ExampleMatcher matcher = ExampleMatcher
            .matching()
            .withIgnorePaths("id")
            .withIgnoreNullValues()
            .withIgnoreCase(true)
            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

    private final AirlineRepository airlineRepository;

    public Optional<Airline> findById(Long id) {
        return airlineRepository.findById(id);
    }

    public Airline create(Airline airline) {
        return airlineRepository.save(airline);
    }

    public Airline update(Airline airline) {
        return airlineRepository.save(airline);
    }

    public Page<Airline> search(Airline airline, Pageable pageable) {
        return airlineRepository.findAll(Example.of(airline, matcher), pageable);
    }

    public Page<Airline> findAll(Pageable pageable) {
        return airlineRepository.findAll(pageable);
    }

    public void delete(Airline airline) {
        airlineRepository.delete(airline);
    }
}
