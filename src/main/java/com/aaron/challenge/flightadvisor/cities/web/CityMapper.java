package com.aaron.challenge.flightadvisor.cities.web;

import com.aaron.challenge.flightadvisor.cities.City;
import com.aaron.challenge.flightadvisor.comments.web.CommentResponse;
import com.aaron.challenge.flightadvisor.config.web.GenericRestMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class CityMapper implements GenericRestMapper<City, CityCreate, CityUpdate, CitySearch, CityResponse> {

    @Override
    public City postToEntity(CityCreate cityCreate) {
        return City.builder()
                .id(UUID.randomUUID().toString())
                .name(cityCreate.getName())
                .country(cityCreate.getCountry())
                .build();
    }

    @Override
    public City putToEntity(CityCreate cityCreate, City target) {
        target.setName(cityCreate.getName());
        target.setCountry(cityCreate.getCountry());

        return target;
    }

    @Override
    public City patchToEntity(CityUpdate cityUpdate, City target) {
        setIfNotNull(cityUpdate::getName, target::setName);
        setIfNotNull(cityUpdate::getCountry, target::setCountry);

        return target;
    }

    @Override
    public City searchToEntity(CitySearch citySearch) {
        return City.builder()
                .name(citySearch.getName())
                .country(citySearch.getCountry())
                .build();
    }

    @Override
    public CityResponse entityToResponse(City city) {
        return CityResponse.builder()
                .id(city.getId())
                .name(city.getName())
                .country(city.getCountry())
                .comments(getComments(city))
                .build();
    }

    private List<CommentResponse> getComments(City city) {
        return city.getComments().stream()
                .map(comment ->
                        CommentResponse.builder()
                                .id(comment.getId())
                                .description(comment.getDescription())
                                .createdDate(comment.getCreatedDate())
                                .modifiedDate(comment.getModifiedDate())
                                .build()
                ).collect(Collectors.toList());
    }
}
