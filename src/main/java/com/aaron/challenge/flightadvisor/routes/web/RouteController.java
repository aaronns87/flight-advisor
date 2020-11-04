package com.aaron.challenge.flightadvisor.routes.web;

import com.aaron.challenge.flightadvisor.routes.RouteService;
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
@RequestMapping("/admin/routes")
@Tag(name = "Routes", description = "World routes between airports in different cities."
)
public class RouteController {

    private final RouteService routeService;

    private final RouteMapper routeMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new route", description = "Creates route.")
    public RouteResponse create(@Valid @RequestBody RouteCreate create) {
        log.info("POST route {}", create.toString());

        return routeMapper.entityToResponse(
                routeService.create(
                        routeMapper.postToEntity(create)
                )
        );
    }

    @PutMapping(path = "/{id}")
    @Operation(summary = "Replace existing route", description = "Replace existing route")
    public RouteResponse replace(@PathVariable Long id, @Valid @RequestBody RouteCreate create) {
        log.info("PUT route id {}, route {}", id, create.toString());

        return routeService
                .findById(id)
                .map(existing -> routeMapper.entityToResponse(
                        routeService.update(
                                routeMapper.putToEntity(create, existing))
                        )
                )
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Route with id " + id + " not found!")
                );
    }

    @PatchMapping(path = "/{id}")
    @Operation(summary = "Partially update existing route", description = "Partially update existing route")
    public RouteResponse update(@PathVariable Long id, @Valid @RequestBody RouteUpdate update) {
        log.info("PATCH route id {}, route {}", id, update.toString());

        return routeService
                .findById(id)
                .map(existing -> routeMapper.entityToResponse(
                        routeService.update(
                                routeMapper.patchToEntity(update, existing))
                        )
                )
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Route with id " + id + " not found!")
                );
    }

    @GetMapping(path = "/{id}")
    @Operation(summary = "Find route", description = "Find route by id.")
    public RouteResponse findOne(@PathVariable Long id) {
        log.info("GET route with id {}", id);

        return routeService
                .findById(id)
                .map(routeMapper::entityToResponse)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Route with id " + id + " not found!")
                );
    }

    @GetMapping("/search")
    @Operation(summary = "Search route", description = "Search route by search criteria.")
    public Page<RouteResponse> search(@Valid @RequestBody RouteSearch search, Pageable pageable) {
        log.info("SEARCH route {} with page {}", search.toString(), pageable.toString());

        return routeMapper.entityToResponsePage(
                routeService.search(
                        routeMapper.searchToEntity(search), pageable
                )
        );
    }

    @GetMapping
    @Operation(summary = "Find all airports paged", description = "Find all airports paged.")
    public Page<RouteResponse> findAll(Pageable pageable) {
        log.info("GET ALL routes with page {}", pageable.toString());

        return routeMapper.entityToResponsePage(
                routeService.findAll(pageable)
        );
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete existing route", description = "Delete existing route.")
    public void delete(@PathVariable Long id) {
        log.info("DELETE route with id {}", id);

        routeService
                .findById(id)
                .ifPresentOrElse(routeService::delete,
                        () -> {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Route with id " + id + " not found!");
                        });
    }
}
