package com.aaron.challenge.flightadvisor.imports.csv;

import com.aaron.challenge.flightadvisor.airports.Airport;
import com.aaron.challenge.flightadvisor.airports.DST;
import com.aaron.challenge.flightadvisor.airports.Type;
import com.aaron.challenge.flightadvisor.cities.City;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor

@Component
public class AirportCsvMapper {

    private static final int FALLBACK_ALTITUDE = Integer.MIN_VALUE;
    private static final double FALLBACK_TIME_ZONE = Double.MIN_VALUE;
    private static final DST FALLBACK_DST = DST.U;
    private static final Type FALLBACK_TYPE = Type.UNKNOWN;

    AirportCsv mapFromCsv(CSVRecord csvRecord) {
        return AirportCsv.builder()
                .externalId(Long.parseLong(csvRecord.get(0)))       // id
                .name(csvRecord.get(1))                             // name
                .cityCsv(CityCsv.builder()
                        .name(csvRecord.get(2))                     // city name
                        .country(csvRecord.get(3))                  // city country
                        .build())
                .iata(csvRecord.get(4))                             // iata
                .icao(csvRecord.get(5))                             // icao
                .latitude(csvRecord.get(6))                         // latitude
                .longitude(csvRecord.get(7))                        // longitude
                .altitude(getAltitudeOrFallback(csvRecord.get(8)))  // altitude
                .timezone(getTimeZoneOrFallback(csvRecord.get(9)))  // timezone
                .dst(getDSTOrFallback(csvRecord.get(10)))           // dst
                .tz(csvRecord.get(11))                              // tz
                .type(getTypeByNameOrFallback(csvRecord.get(12)))   // type
                .source(csvRecord.get(13))                          // source
                .build();
    }

    Airport toAirport(AirportCsv airportCsv, City city) {
        return Airport.builder()
                .id(UUID.randomUUID().toString())
                .externalId(airportCsv.getExternalId())  // We are mapping external ids during CSV import since they are provided in CSV
                .name(airportCsv.getName())
                .city(city)
                .iata(airportCsv.getIata())
                .icao(airportCsv.getIcao())
                .latitude(airportCsv.getLatitude())
                .longitude(airportCsv.getLongitude())
                .altitude(airportCsv.getAltitude())
                .timeZone(airportCsv.getTimezone())
                .dst(airportCsv.getDst())
                .tz(airportCsv.getTz())
                .type(airportCsv.getType())
                .source(airportCsv.getSource())
                .build();
    }

    private Integer getAltitudeOrFallback(String altitude) {
        return StringUtils.isNotBlank(altitude) ? Integer.parseInt(altitude) : FALLBACK_ALTITUDE;
    }

    private Double getTimeZoneOrFallback(String timeZone) {
        return StringUtils.isNotBlank(timeZone) ? Double.parseDouble(timeZone) : FALLBACK_TIME_ZONE;
    }

    private DST getDSTOrFallback(String dst) {
        return StringUtils.isNotBlank(dst) ? DST.valueOf(dst) : FALLBACK_DST;
    }

    private Type getTypeByNameOrFallback(String name) {
        return Type.getByName(name).orElse(FALLBACK_TYPE);
    }
}
