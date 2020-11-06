package com.aaron.challenge.flightadvisor.routes;

import com.aaron.challenge.flightadvisor.airlines.Airline;
import com.aaron.challenge.flightadvisor.airports.Airport;
import com.aaron.challenge.flightadvisor.routes.cheapest.CheapestFlightChain;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@SqlResultSetMapping(name = "CheapestFlightChain", classes = {
        @ConstructorResult(
                targetClass = CheapestFlightChain.class,
                columns = {
                        @ColumnResult(name = "routes", type = String.class),
                        @ColumnResult(name = "totalPrice", type = Float.class),
                        @ColumnResult(name = "hops", type = Integer.class)
                })
})

@NamedNativeQueries({
        @NamedNativeQuery(
                name = "Route.findCheapestFlightChain",
                query = "WITH RECURSIVE flight_chain( " +
                        "  source_city_mapping, " +
                        "  destination_city_mapping, " +
                        "  total_price, " +
                        "  sources, " +
                        "  destinations, " +
                        "  full_path, " +
                        "  routes, " +
                        "  hops " +
                        ") AS ( " +
                        "        SELECT " +
                        "          r.source_city_mapping, " +
                        "          r.destination_city_mapping, " +
                        "          r.price AS total_price, " +
                        "          ARRAY[r.source_city_mapping] AS sources, " +
                        "          ARRAY[r.destination_city_mapping] AS destinations, " +
                        "          ARRAY[array[r.source_city_mapping, r.destination_city_mapping]] AS full_path, " +
                        "          ARRAY[CAST(r.id AS text)] AS routes, " +
                        "          1 AS hops " +
                        "        FROM route AS r " +
                        "        WHERE " +
                        "          source_city_mapping = :sourceCityMapping " +
                        "      UNION ALL " +
                        "        SELECT " +
                        "          r.source_city_mapping, " +
                        "          r.destination_city_mapping, " +
                        "          fc.total_price + r.price AS total_price, " +
                        "          sources || r.source_city_mapping AS sources, " +
                        "          destinations || r.destination_city_mapping AS destinations, " +
                        "          full_path || ARRAY[r.source_city_mapping, r.destination_city_mapping] AS full_path, " +
                        "          routes || CAST(r.id AS text) AS routes, " +
                        "          fc.hops + 1 AS hops " +
                        "        FROM route AS r, flight_chain AS fc " +
                        "        WHERE " +
                        "          r.source_city_mapping = fc.destination_city_mapping " +
                        "          AND (r.source_city_mapping <> ALL(fc.sources)) " +
                        "          AND (r.destination_city_mapping <> ALL(fc.destinations)) " +
                        "          AND fc.hops < :maxHops " +
                        ") " +
                        "SELECT CAST(routes AS TEXT) AS routes, " +
                        "total_price AS totalPrice, " +
                        "hops AS hops," +
                        "destinations AS destinations " +
                        "FROM flight_chain WHERE :destinationCityMapping = destinations[array_upper(destinations, 1)] " +
                        "ORDER BY totalPrice ASC " +
                        "LIMIT :maxResults ",
                resultSetMapping = "CheapestFlightChain",
                resultClass = CheapestFlightChain.class
        )}
)

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Route {

    @Id
    @NaturalId
    @Column(nullable = false)
    private String id;

    @Column(nullable = false)
    private String airlineCode;

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    private Airline airline;

    @Column(nullable = false)
    private String sourceAirportCode;

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    private Airport sourceAirport;

    @Column(nullable = false)
    private String destinationAirportCode;

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    private Airport destinationAirport;

    @Column(nullable = false)
    private Boolean codeShare;

    @Column(nullable = false)
    private Integer stops;

    @Column(nullable = false)
    private String equipment;

    private Float price;

    private Integer sourceCityMapping;

    private Integer destinationCityMapping;
}
