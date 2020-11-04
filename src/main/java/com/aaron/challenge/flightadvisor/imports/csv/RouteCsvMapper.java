package com.aaron.challenge.flightadvisor.imports.csv;

import com.aaron.challenge.flightadvisor.airlines.Airline;
import com.aaron.challenge.flightadvisor.airports.Airport;
import com.aaron.challenge.flightadvisor.routes.Route;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RouteCsvMapper {

    private static final int FALLBACK_STOPS = Integer.MIN_VALUE;
    private static final float FALLBACK_PRICE = Float.MIN_VALUE;

    RouteCsv mapFromCsv(CSVRecord csvRecord) {
        return RouteCsv.builder()
                .airlineCsv(AirlineCsv.builder()
                        .code(csvRecord.get(0))                                         // airline code
                        .externalId(getAirlineExternalIdOrFallback(csvRecord.get(1)))   // airline id
                        .build())
                .sourceAirportCode(csvRecord.get(2))                                    // source airport code
                .sourceAirportId(getSourceAirportIdOrThrow(csvRecord.get(3)))           // source airport id
                .destinationAirportCode(csvRecord.get(4))                               // destination airport code
                .destinationAirportId(getDestinationAirportIdOrThrow(csvRecord.get(5))) // destination airport id
                .codeShare(StringUtils.isNotBlank(csvRecord.get(6)))                    // code share
                .stops(getStopsOrFallback(csvRecord.get(7)))                            // stops
                .equipment(csvRecord.get(8))                                            // equipment
                .price(getPriceOrFallback(csvRecord.get(9)))                            // price
                .build();
    }

    Route toRoute(RouteCsv routeCsv, Airline airline, Airport sourceAirport, Airport destinationAirport) {
        return Route.builder()
                .id(UUID.randomUUID().toString())
                .airlineCode(routeCsv.getAirlineCsv().getCode())
                .airline(airline)
                .sourceAirportCode(routeCsv.getSourceAirportCode())
                .sourceAirport(sourceAirport)
                .destinationAirportCode(routeCsv.getDestinationAirportCode())
                .destinationAirport(destinationAirport)
                .codeShare(routeCsv.getCodeShare())
                .stops(routeCsv.getStops())
                .equipment(routeCsv.getEquipment())
                .price(routeCsv.getPrice())
                .build();
    }

    private Long getAirlineExternalIdOrFallback(String externalId) {
        return StringUtils.isNotBlank(externalId) ? Long.parseLong(externalId) : null;
    }

    private Long getSourceAirportIdOrThrow(String sourceAirportId) {
        if (StringUtils.isNotBlank(sourceAirportId)) {
            return Long.parseLong(sourceAirportId);
        } else {
            throw new IllegalStateException("Source airport id is not allowed to be null!");
        }
    }

    private Long getDestinationAirportIdOrThrow(String destinationAirportId) {
        if (StringUtils.isNotBlank(destinationAirportId)) {
            return Long.parseLong(destinationAirportId);
        } else {
            throw new IllegalStateException("Source airport id is not allowed to be null!");
        }
    }

    private Integer getStopsOrFallback(String stops) {
        return StringUtils.isNotBlank(stops) ? Integer.parseInt(stops) : FALLBACK_STOPS;
    }

    private Float getPriceOrFallback(String price) {
        return StringUtils.isNotBlank(price) ? Float.parseFloat(price) : FALLBACK_PRICE;
    }
}
