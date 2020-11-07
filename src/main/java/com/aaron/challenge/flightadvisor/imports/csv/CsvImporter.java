package com.aaron.challenge.flightadvisor.imports.csv;

import com.aaron.challenge.flightadvisor.config.error.imports.MissingImportFieldException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor

@Component
public class CsvImporter {

    private static final String NULL_STRING = "\\N";
    private static final char DELIMITER = ',';
    private static final char QUOTE = '"';

    private final AirportCsvMapper airportCsvMapper;
    private final AirportCsvPersist airportCsvPersist;

    private final RouteCsvMapper routeCsvMapper;
    private final RouteCsvPersist routeCsvPersist;

    public void importAirports(InputStream inputStream) throws IOException {
        storeAirportImportToDb(
                toAirportCsvStream(inputStream)
        );
    }

    public void importRoutes(InputStream inputStream) throws IOException {
        storeRouteImportToDb(
                toRouteCsvStream(inputStream)
        );
    }

    private Stream<AirportCsv> toAirportCsvStream(InputStream inputStream) throws IOException {
        try (BufferedReader bufferedReader = getBufferedReader(inputStream); CSVParser csvParser = getCsvParser(bufferedReader)) {
            return csvParser
                    .getRecords().stream()
                    .map(this::tryMapAirportCsv)
                    .filter(Objects::nonNull);
        }
    }

    private AirportCsv tryMapAirportCsv(CSVRecord csvRecord) {
        try {
            return airportCsvMapper.mapFromCsv(csvRecord);
        } catch (Exception e) {
            log.warn("Unable to map airport csv record {} due to error.", csvRecord.toString(), e);
            return null;
        }
    }

    private void storeAirportImportToDb(Stream<AirportCsv> airportCsvStream) {
        airportCsvStream.forEach(airportCsvPersist::store);
    }

    private Stream<RouteCsv> toRouteCsvStream(InputStream inputStream) throws IOException {
        try (BufferedReader bufferedReader = getBufferedReader(inputStream); CSVParser csvParser = getCsvParser(bufferedReader)) {
            return csvParser
                    .getRecords().stream()
                    .map(this::tryMapRouteCsv)
                    .filter(Objects::nonNull);
        }
    }

    private void storeRouteImportToDb(Stream<RouteCsv> routeCsvStream) {
        routeCsvStream.forEach(routeCsvPersist::store);
    }

    private RouteCsv tryMapRouteCsv(CSVRecord csvRecord) {
        try {
            return routeCsvMapper.mapFromCsv(csvRecord);
        } catch (MissingImportFieldException e) {
            log.warn("Unable to map route csv record {} due to missing import field {}", csvRecord.toString(), e.getMessage());
            return null;
        } catch (Exception e) {
            log.warn("Unable to map route csv record {} due to error.", csvRecord.toString(), e);
            return null;
        }
    }

    private BufferedReader getBufferedReader(InputStream inputStream) {
        return new BufferedReader(
                new InputStreamReader(
                        inputStream,
                        StandardCharsets.UTF_8
                )
        );
    }

    private CSVParser getCsvParser(BufferedReader bufferedReader) throws IOException {
        return new CSVParser(
                bufferedReader,
                CSVFormat.DEFAULT
                        .withIgnoreSurroundingSpaces()
                        .withIgnoreEmptyLines()
                        .withNullString(NULL_STRING)
                        .withDelimiter(DELIMITER)
                        .withQuote(QUOTE)
                        .withTrim()
        );
    }
}
