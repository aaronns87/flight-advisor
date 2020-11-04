package com.aaron.challenge.flightadvisor.airports;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface AirportRepository extends JpaRepository<Airport, String> {

    Optional<Airport> findByExternalId(Long externalId);
}
