package com.aaron.challenge.flightadvisor.flights.web;

import com.aaron.challenge.flightadvisor.flights.FlightService;
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
public class FlightController {

    private final FlightService flightService;

    @GetMapping("/cheapest")
    @Operation(summary = "Search cheapest flight route.", description = "Search for cheapest flight route between two cities. " +
            "Provide source city id or source city name & country combination together with " +
            "destination city id or destination city name & country combination. " +
            "Results are returned ordered by total price of route chain, cheapest first and ascending towards more expensive ones. " +
            "Providing max hops (chain length) and maximum returned results is also possible. " +
            "Be aware that increasing hops count increases request duration.")
    public FlightSearchResponse searchCheapest(@Valid @RequestBody FlightSearchRequest flightSearchRequest) {
        log.info("GET cheapest route {}", flightSearchRequest.toString());

        return flightService.searchCheapestRoute(flightSearchRequest);
    }
}
