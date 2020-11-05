package com.aaron.challenge.flightadvisor.imports.csv;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class CityCsvMapperTest {

    @InjectMocks
    private CityCsvMapper cityCsvMapper;

    @Test
    public void toCity_cityCsv_city() {
        var cityCsv = CityCsv.builder()
                .name("name")
                .country("country")
                .build();

        var city = cityCsvMapper.toCity(cityCsv);

        assertThat(city.getName()).isEqualTo("name");
        assertThat(city.getCountry()).isEqualTo("country");
    }
}
