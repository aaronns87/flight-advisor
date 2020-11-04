package com.aaron.challenge.flightadvisor.cities.web;

import com.aaron.challenge.flightadvisor.cities.City;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class CityMapperTest {

    @InjectMocks
    private CityMapper cityMapper;

    @Test
    public void postToEntity_airportCreate_city() {
        var cityCreate = new CityCreate();
        cityCreate.setName("name");
        cityCreate.setCountry("country");

        var city = cityMapper.postToEntity(cityCreate);

        assertThat(city.getId()).isNotBlank();
        assertThat(city.getName()).isEqualTo("name");
        assertThat(city.getCountry()).isEqualTo("country");
    }

    @Test
    public void putToEntity_cityCreate_target_city() {
        var cityCreate = new CityCreate();
        cityCreate.setName("name");
        cityCreate.setCountry("country");

        var target = new City();

        var city = cityMapper.putToEntity(cityCreate, target);

        assertThat(city.getName()).isEqualTo("name");
        assertThat(city.getCountry()).isEqualTo("country");
    }

    @Test
    public void patchToEntity_cityUpdate_target_city() {
        var cityUpdate = new CityUpdate();
        cityUpdate.setName("name");
        cityUpdate.setCountry("country");

        var target = new City();

        var city = cityMapper.patchToEntity(cityUpdate, target);

        assertThat(city.getName()).isEqualTo("name");
        assertThat(city.getCountry()).isEqualTo("country");
    }

    @Test
    public void searchToEntity_citySearch_city() {
        var citySearch = new CitySearch();
        citySearch.setName("name");
        citySearch.setCountry("country");

        var city = cityMapper.searchToEntity(citySearch);

        assertThat(city.getName()).isEqualTo("name");
        assertThat(city.getCountry()).isEqualTo("country");
    }

    @Test
    public void entityToResponse_city_cityResponse() {
        var city = new City();
        city.setId("id");
        city.setName("name");
        city.setCountry("country");

        var cityResponse = cityMapper.entityToResponse(city);

        assertThat(cityResponse.getId()).isEqualTo("id");
        assertThat(cityResponse.getName()).isEqualTo("name");
        assertThat(cityResponse.getCountry()).isEqualTo("country");
    }
}
