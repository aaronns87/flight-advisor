package com.aaron.challenge.flightadvisor.imports.csv;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.testcontainers.shaded.org.apache.commons.io.IOUtils;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CsvImporterTest {

    @InjectMocks
    private CsvImporter csvImporter;

    @Mock
    private AirportCsvMapper airportCsvMapper;

    @Mock
    private AirportCsvPersist airportCsvPersist;

    @Mock
    private RouteCsvMapper routeCsvMapper;

    @Mock
    private RouteCsvPersist routeCsvPersist;

    @Test
    public void importAirports_inputStream() throws IOException {
        when(airportCsvMapper.mapFromCsv(any())).thenReturn(AirportCsv.builder().build());

        csvImporter.importAirports(IOUtils.toInputStream("a,b,c", "UTF-8"));

        verify(airportCsvPersist).store(any());
    }

    @Test
    public void importRoutes_inputStream() throws IOException {
        when(routeCsvMapper.mapFromCsv(any())).thenReturn(RouteCsv.builder().build());

        csvImporter.importRoutes(IOUtils.toInputStream("a,b,c", "UTF-8"));

        verify(routeCsvPersist).store(any());
    }
}
