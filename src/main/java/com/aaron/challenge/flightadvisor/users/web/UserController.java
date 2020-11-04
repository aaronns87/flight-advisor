package com.aaron.challenge.flightadvisor.users.web;

import com.aaron.challenge.flightadvisor.users.UserService;
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
@RequestMapping("/admin/users")
@Tag(name = "Users", description = "System users. Types: Administrators and Users (regular users)."
)
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new user", description = "Creates user.")
    public UserResponse create(@Valid @RequestBody UserCreate create) {
        log.info("POST user {}", create.toString());

        return userMapper.entityToResponse(
                userService.create(
                        userMapper.postToEntity(create)
                )
        );
    }

    @PutMapping(path = "/{id}")
    @Operation(summary = "Replace existing user", description = "Replace existing user")
    public UserResponse replace(@PathVariable Long id, @Valid @RequestBody UserCreate create) {
        log.info("PUT user id {}, user {}", id, create.toString());

        return userService
                .findById(id)
                .map(existing -> userMapper.entityToResponse(
                        userService.update(
                                userMapper.putToEntity(create, existing))
                        )
                )
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id " + id + " not found!")
                );
    }

    @PatchMapping(path = "/{id}")
    @Operation(summary = "Partially update existing user", description = "Partially update existing user")
    public UserResponse update(@PathVariable Long id, @Valid @RequestBody UserUpdate update) {
        log.info("PATCH user id {}, user {}", id, update.toString());

        return userService
                .findById(id)
                .map(existing -> userMapper.entityToResponse(
                        userService.update(
                                userMapper.patchToEntity(update, existing))
                        )
                )
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id " + id + " not found!")
                );
    }

    @GetMapping(path = "/{id}")
    @Operation(summary = "Find user", description = "Find user by id.")
    public UserResponse findOne(@PathVariable Long id) {
        log.info("GET user with id {}", id);

        return userService
                .findById(id)
                .map(userMapper::entityToResponse)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id " + id + " not found!")
                );
    }

    @GetMapping("/search")
    @Operation(summary = "Search user", description = "Search user by search criteria.")
    public Page<UserResponse> search(@Valid @RequestBody UserSearch search, Pageable pageable) {
        log.info("SEARCH user {} with page {}", search.toString(), pageable.toString());

        return userMapper.entityToResponsePage(
                userService.search(
                        userMapper.searchToEntity(search), pageable
                )
        );
    }

    @GetMapping
    @Operation(summary = "Find all users paged", description = "Find all users paged.")
    public Page<UserResponse> findAll(Pageable pageable) {
        log.info("GET ALL users with page {}", pageable.toString());

        return userMapper.entityToResponsePage(
                userService.findAll(pageable)
        );
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete existing user", description = "Delete existing user.")
    public void delete(@PathVariable Long id) {
        log.info("DELETE user with id {}", id);

        userService
                .findById(id)
                .ifPresentOrElse(userService::delete,
                        () -> {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id " + id + " not found!");
                        });
    }
}
