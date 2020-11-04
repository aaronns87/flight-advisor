package com.aaron.challenge.flightadvisor.airports;

import org.springframework.data.jpa.repository.JpaRepository;

interface AirportRepository extends JpaRepository<Airport, Long> {

}
