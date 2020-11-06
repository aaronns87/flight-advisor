package com.aaron.challenge.flightadvisor.routes;

import com.aaron.challenge.flightadvisor.routes.cheapest.CheapestFlightChain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RouteRepository extends JpaRepository<Route, String> {

    @Query(nativeQuery = true)
    List<CheapestFlightChain> findCheapestFlightChain(@Param("sourceCityMapping") Integer sourceCityMapping,
                                                      @Param("destinationCityMapping") Integer destinationCityMapping,
                                                      @Param("maxHops") Integer maxHops,
                                                      @Param("maxResults") Integer maxResults);
}

