package com.aaron.challenge.flightadvisor.comments.web;

import com.aaron.challenge.flightadvisor.cities.City;
import com.aaron.challenge.flightadvisor.cities.CityService;
import com.aaron.challenge.flightadvisor.cities.web.CityResponse;
import com.aaron.challenge.flightadvisor.comments.Comment;
import com.aaron.challenge.flightadvisor.config.web.GenericRestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;


@RequiredArgsConstructor

@Component
public class CommentMapper implements GenericRestMapper<Comment, CommentCreate, CommentUpdate, CommentSearch, CommentResponse> {

    private final CityService cityService;

    @Override
    public Comment postToEntity(CommentCreate commentCreate) {
        return Comment.builder()
                .id(UUID.randomUUID().toString())
                .city(findCityOrThrow(commentCreate.getCityId()))
                .description(commentCreate.getDescription())
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .build();
    }

    @Override
    public Comment putToEntity(CommentCreate commentCreate, Comment target) {
        target.setCity(findCityOrThrow(commentCreate.getCityId()));
        target.setDescription(commentCreate.getDescription());
        target.setModifiedDate(LocalDateTime.now());

        return target;
    }

    @Override
    public Comment patchToEntity(CommentUpdate commentUpdate, Comment target) {
        setIfNotNull(commentUpdate::getDescription, target::setDescription);

        target.setModifiedDate(LocalDateTime.now());

        return target;
    }

    @Override
    public Comment searchToEntity(CommentSearch commentSearch) {
        return Comment.builder()
                .description(commentSearch.getDescription())
                .build();
    }

    @Override
    public CommentResponse entityToResponse(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .city(CityResponse.builder()
                        .id(comment.getCity().getId())
                        .name(comment.getCity().getName())
                        .country(comment.getCity().getCountry())
                        .build())
                .description(comment.getDescription())
                .createdDate(comment.getCreatedDate())
                .modifiedDate(comment.getModifiedDate())
                .build();
    }

    private City findCityOrThrow(String id) {
        return cityService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "City with id " + id + " not found."));
    }
}
