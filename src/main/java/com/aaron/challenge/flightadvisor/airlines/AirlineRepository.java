package com.aaron.challenge.flightadvisor.airlines;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface AirlineRepository extends JpaRepository<Airline, String> {

    Optional<Airline> findByExternalId(Long externalId);
}
