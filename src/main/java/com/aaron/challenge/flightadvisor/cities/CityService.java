package com.aaron.challenge.flightadvisor.cities;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor

@Service
public class CityService {

    public static final ExampleMatcher matcher = ExampleMatcher
            .matching()
            .withIgnorePaths("id")
            .withIgnoreNullValues()
            .withIgnoreCase(true)
            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

    private final CityRepository cityRepository;

    public Optional<City> findById(String id) {
        return cityRepository.findById(id);
    }

    public Optional<City> findByNameAndCountry(String name, String country) {
        return cityRepository.findByNameAndCountry(name, country);
    }

    public City create(City city) {
        return cityRepository.save(city);
    }

    public City update(City city) {
        return cityRepository.save(city);
    }

    public Page<City> search(City city, Pageable pageable) {
        return cityRepository.findAll(Example.of(city, matcher), pageable);
    }

    public Page<City> findAll(Pageable pageable) {
        return cityRepository.findAll(pageable);
    }

    public void delete(City city) {
        cityRepository.delete(city);
    }
}
