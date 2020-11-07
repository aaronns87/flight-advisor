package com.aaron.challenge.flightadvisor.cities.web;

import com.aaron.challenge.flightadvisor.cities.CityService;
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
@RequestMapping
@Tag(name = "Cities", description = "World cities where airports exist."
)
public class CityController {

    private final CityService cityService;

    private final CityMapper cityMapper;

    @PostMapping("/admin/cities")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new city", description = "Creates city.")
    public CityResponse create(@Valid @RequestBody CityCreate create) {
        log.info("POST city {}", create.toString());

        return cityMapper.entityToResponse(
                cityService.create(
                        cityMapper.postToEntity(create)
                )
        );
    }

    @PutMapping(path = "/admin/cities/{id}")
    @Operation(summary = "Replace existing city", description = "Replace existing city")
    public CityResponse replace(@PathVariable String id, @Valid @RequestBody CityCreate create) {
        log.info("PUT city id {}, city {}", id, create.toString());

        return cityService
                .findById(id)
                .map(existing -> cityMapper.entityToResponse(
                        cityService.update(
                                cityMapper.putToEntity(create, existing))
                        )
                )
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "City with id " + id + " not found!")
                );
    }

    @PatchMapping(path = "/admin/cities/{id}")
    @Operation(summary = "Partially update existing city", description = "Partially update existing city")
    public CityResponse update(@PathVariable String id, @Valid @RequestBody CityUpdate update) {
        log.info("PATCH city id {}, city {}", id, update.toString());

        return cityService
                .findById(id)
                .map(existing -> cityMapper.entityToResponse(
                        cityService.update(
                                cityMapper.patchToEntity(update, existing))
                        )
                )
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "City with id " + id + " not found!")
                );
    }

    @GetMapping(path = "/admin/cities/{id}")
    @Operation(summary = "Find city", description = "Find city by id.")
    public CityResponse findOne(@PathVariable String id) {
        log.info("GET city with id {}", id);

        return cityService
                .findById(id)
                .map(cityMapper::entityToResponse)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "City with id " + id + " not found!")
                );
    }

    @GetMapping("/admin/cities/search")
    @Operation(summary = "Search city", description = "Search city by search criteria.")
    public Page<CityResponse> search(@Valid @RequestBody CitySearch search, Pageable pageable) {
        log.info("SEARCH city {} with page {}", search.toString(), pageable.toString());

        return cityMapper.entityToResponsePage(
                cityService.search(
                        cityMapper.searchToEntity(search), pageable
                )
        );
    }

    // Was fastest just to expose a root get endpoint for users
    @GetMapping("/cities/search")
    @Operation(summary = "User search city", description = "User search city by search criteria.")
    public Page<CityResponse> userSearch(@Valid @RequestBody CitySearch search, Pageable pageable) {
        log.info("USER SEARCH city {} with page {}", search.toString(), pageable.toString());

        return search(search, pageable);
    }

    @GetMapping("/admin/cities")
    @Operation(summary = "Find all cities paged", description = "Find all cities paged.")
    public Page<CityResponse> findAll(Pageable pageable) {
        log.info("GET ALL cities with page {}", pageable.toString());

        return cityMapper.entityToResponsePage(
                cityService.findAll(pageable)
        );
    }

    // Was fastest just to expose a root get endpoint for users
    @GetMapping("/cities")
    @Operation(summary = "User find all cities paged", description = "User find all cities paged.")
    public Page<CityResponse> userFindAll(Pageable pageable) {
        log.info("USER GET ALL cities with page {}", pageable.toString());

        return findAll(pageable);
    }

    @DeleteMapping(path = "/admin/cities/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete existing city", description = "Delete existing city.")
    public void delete(@PathVariable String id) {
        log.info("DELETE city with id {}", id);

        cityService
                .findById(id)
                .ifPresentOrElse(cityService::delete,
                        () -> {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "City with id " + id + " not found!");
                        });
    }
}
