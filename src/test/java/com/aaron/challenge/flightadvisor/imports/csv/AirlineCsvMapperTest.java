package com.aaron.challenge.flightadvisor.imports.csv;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class AirlineCsvMapperTest {

    @InjectMocks
    private AirlineCsvMapper airlineCsvMapper;

    public void toAirline_airlineCsv_airline() {
        var airlineCsv = AirlineCsv.builder()
                .code("code")
                .externalId(1L)
                .build();

        var airline = airlineCsvMapper.toAirline(airlineCsv);

        assertThat(airline.getCode()).isEqualTo("code");
        assertThat(airline.getExternalId()).isEqualTo(1L);
    }
}
