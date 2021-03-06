package com.aaron.challenge.flightadvisor.cities;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface CityRepository extends JpaRepository<City, String> {

    Optional<City> findByNameAndCountry(String name, String country);
}
