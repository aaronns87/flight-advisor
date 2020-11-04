package com.aaron.challenge.flightadvisor.airlines;

import org.springframework.data.jpa.repository.JpaRepository;

interface AirlineRepository extends JpaRepository<Airline, Long> {

}
