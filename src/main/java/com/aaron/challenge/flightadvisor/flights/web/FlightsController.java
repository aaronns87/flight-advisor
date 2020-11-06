package com.aaron.challenge.flightadvisor.flights.web;

import com.aaron.challenge.flightadvisor.flights.FlightsService;
import com.aaron.challenge.flightadvisor.flights.web.request.FlightSearchRequest;
import com.aaron.challenge.flightadvisor.flights.web.response.FlightSearchResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor

@RestController
@RequestMapping("/flights")
@Tag(name = "Flights", description = "Search flights and routes."
)
public class FlightsController {

    private final FlightsService flightsService;

    @GetMapping("/cheapest")
    @Operation(summary = "Search cheapest flight route.", description = "Search for cheapest flight route between two cities.")
    public FlightSearchResponse searchCheapest(@Valid @RequestBody FlightSearchRequest flightSearchRequest) {
        log.info("GET cheapest route {}", flightSearchRequest.toString());

        return flightsService.searchCheapestRoute(flightSearchRequest);
    }
}
