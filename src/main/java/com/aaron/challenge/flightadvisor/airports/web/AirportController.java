package com.aaron.challenge.flightadvisor.airports.web;

import com.aaron.challenge.flightadvisor.airports.AirportService;
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
@RequestMapping("/admin/airports")
@Tag(name = "Airports", description = "Airports."
)
public class AirportController {

    private final AirportService airportService;

    private final AirportMapper airportMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new airport", description = "Creates airport.")
    public AirportResponse create(@Valid @RequestBody AirportCreate create) {
        log.info("POST airport {}", create.toString());

        return airportMapper.entityToResponse(
                airportService.create(
                        airportMapper.postToEntity(create)
                )
        );
    }

    @PutMapping(path = "/{id}")
    @Operation(summary = "Replace existing airport", description = "Replace existing airport")
    public AirportResponse replace(@PathVariable String id, @Valid @RequestBody AirportCreate create) {
        log.info("PUT airport id {}, airport {}", id, create.toString());

        return airportService
                .findById(id)
                .map(existing -> airportMapper.entityToResponse(
                        airportService.update(
                                airportMapper.putToEntity(create, existing))
                        )
                )
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Airport with id " + id + " not found!")
                );
    }

    @PatchMapping(path = "/{id}")
    @Operation(summary = "Partially update existing airport", description = "Partially update existing airport")
    public AirportResponse update(@PathVariable String id, @Valid @RequestBody AirportUpdate update) {
        log.info("PATCH airport id {}, airport {}", id, update.toString());

        return airportService
                .findById(id)
                .map(existing -> airportMapper.entityToResponse(
                        airportService.update(
                                airportMapper.patchToEntity(update, existing))
                        )
                )
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Airport with id " + id + " not found!")
                );
    }

    @GetMapping(path = "/{id}")
    @Operation(summary = "Find airport", description = "Find airport by id.")
    public AirportResponse findOne(@PathVariable String id) {
        log.info("GET airport with id {}", id);

        return airportService
                .findById(id)
                .map(airportMapper::entityToResponse)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Airport with id " + id + " not found!")
                );
    }

    @GetMapping("/search")
    @Operation(summary = "Search airport", description = "Search airport by search criteria.")
    public Page<AirportResponse> search(@Valid @RequestBody AirportSearch search, Pageable pageable) {
        log.info("SEARCH airport {} with page {}", search.toString(), pageable.toString());

        return airportMapper.entityToResponsePage(
                airportService.search(
                        airportMapper.searchToEntity(search), pageable
                )
        );
    }

    @GetMapping
    @Operation(summary = "Find all airports paged", description = "Find all airports paged.")
    public Page<AirportResponse> findAll(Pageable pageable) {
        log.info("GET ALL airports with page {}", pageable.toString());

        return airportMapper.entityToResponsePage(
                airportService.findAll(pageable)
        );
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete existing airport", description = "Delete existing airport.")
    public void delete(@PathVariable String id) {
        log.info("DELETE airport with id {}", id);

        airportService
                .findById(id)
                .ifPresentOrElse(airportService::delete,
                        () -> {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Airport with id " + id + " not found!");
                        });
    }
}
