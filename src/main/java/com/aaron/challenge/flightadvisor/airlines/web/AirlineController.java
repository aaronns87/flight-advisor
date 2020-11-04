package com.aaron.challenge.flightadvisor.airlines.web;

import com.aaron.challenge.flightadvisor.airlines.AirlineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor

@RestController
@RequestMapping("/admin/airlines")
@Tag(name = "Airlines", description = "Helper controller for airlines, mostly designed to be used for integration tests and data setup."
)
public class AirlineController {

    private final AirlineService airlineService;

    private final AirlineMapper airlineMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new airline", description = "Creates airline.")
    public AirlineResponse create(@Valid @RequestBody AirlineCreate create) {
        log.info("POST airline {}", create.toString());

        return airlineMapper.entityToResponse(
                airlineService.create(
                        airlineMapper.postToEntity(create)
                )
        );
    }

    @PutMapping(path = "/{id}")
    @Operation(summary = "Replace existing airline", description = "Replace existing airline")
    public AirlineResponse replace(@PathVariable String id, @Valid @RequestBody AirlineCreate create) {
        log.info("PUT airline id {}, airport {}", id, create.toString());

        return airlineService
                .findById(id)
                .map(existing -> airlineMapper.entityToResponse(
                        airlineService.update(
                                airlineMapper.putToEntity(create, existing))
                        )
                )
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Airline with id " + id + " not found!")
                );
    }

    @PatchMapping(path = "/{id}")
    @Operation(summary = "Partially update existing airline", description = "Partially update existing airline")
    public AirlineResponse update(@PathVariable String id, @Valid @RequestBody AirlineUpdate update) {
        log.info("PATCH airline id {}, airline {}", id, update.toString());

        return airlineService
                .findById(id)
                .map(existing -> airlineMapper.entityToResponse(
                        airlineService.update(
                                airlineMapper.patchToEntity(update, existing))
                        )
                )
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Airline with id " + id + " not found!")
                );
    }

    @GetMapping(path = "/{id}")
    @Operation(summary = "Find airline", description = "Find airline by id.")
    public AirlineResponse findOne(@PathVariable String id) {
        log.info("GET airline with id {}", id);

        return airlineService
                .findById(id)
                .map(airlineMapper::entityToResponse)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Airline with id " + id + " not found!")
                );
    }

    @GetMapping("/search")
    @Operation(summary = "Search airline", description = "Search airline by search criteria.")
    public Page<AirlineResponse> search(@Valid @RequestBody AirlineSearch search, Pageable pageable) {
        log.info("SEARCH airline {} with page {}", search.toString(), pageable.toString());

        return airlineMapper.entityToResponsePage(
                airlineService.search(
                        airlineMapper.searchToEntity(search), pageable
                )
        );
    }

    @GetMapping
    @Operation(summary = "Find all airlines paged", description = "Find all airlines paged.")
    public Page<AirlineResponse> findAll(Pageable pageable) {
        log.info("GET ALL airlines with page {}", pageable.toString());

        return airlineMapper.entityToResponsePage(
                airlineService.findAll(pageable)
        );
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete existing airline", description = "Delete existing airline.")
    public void delete(@PathVariable String id) {
        log.info("DELETE airline with id {}", id);

        airlineService
                .findById(id)
                .ifPresentOrElse(airlineService::delete,
                        () -> {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Airline with id " + id + " not found!");
                        });
    }
}
