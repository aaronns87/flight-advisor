package com.aaron.challenge.flightadvisor.comments.web;

import com.aaron.challenge.flightadvisor.comments.CommentService;
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
@RequestMapping("/comments")
@Tag(name = "Comments", description = "City comments left by regular users."
)
public class CommentController {

    private final CommentService commentService;

    private final CommentMapper commentMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new comment", description = "Creates comment.")
    public CommentResponse create(@Valid @RequestBody CommentCreate create) {
        log.info("POST comment {}", create.toString());

        return commentMapper.entityToResponse(
                commentService.create(
                        commentMapper.postToEntity(create)
                )
        );
    }

    @PutMapping(path = "/{id}")
    @Operation(summary = "Replace existing comment", description = "Replace existing comment")
    public CommentResponse replace(@PathVariable String id, @Valid @RequestBody CommentCreate create) {
        log.info("PUT comment id {}, comment {}", id, create.toString());

        return commentService
                .findById(id)
                .map(existing -> commentMapper.entityToResponse(
                        commentService.update(
                                commentMapper.putToEntity(create, existing))
                        )
                )
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment with id " + id + " not found!")
                );
    }

    @PatchMapping(path = "/{id}")
    @Operation(summary = "Partially update existing comment", description = "Partially update existing comment")
    public CommentResponse update(@PathVariable String id, @Valid @RequestBody CommentUpdate update) {
        log.info("PATCH comment id {}, comment {}", id, update.toString());

        return commentService
                .findById(id)
                .map(existing -> commentMapper.entityToResponse(
                        commentService.update(
                                commentMapper.patchToEntity(update, existing))
                        )
                )
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment with id " + id + " not found!")
                );
    }

    @GetMapping(path = "/{id}")
    @Operation(summary = "Find comment", description = "Find comment by id.")
    public CommentResponse findOne(@PathVariable String id) {
        log.info("GET comment with id {}", id);

        return commentService
                .findById(id)
                .map(commentMapper::entityToResponse)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment with id " + id + " not found!")
                );
    }

    @GetMapping("/search")
    @Operation(summary = "Search comment", description = "Search comment by search criteria.")
    public Page<CommentResponse> search(@Valid @RequestBody CommentSearch search, Pageable pageable) {
        log.info("SEARCH comment {} with page {}", search.toString(), pageable.toString());

        return commentMapper.entityToResponsePage(
                commentService.search(
                        commentMapper.searchToEntity(search), pageable
                )
        );
    }

    @GetMapping
    @Operation(summary = "Find all comments paged", description = "Find all comments paged.")
    public Page<CommentResponse> findAll(Pageable pageable) {
        log.info("GET ALL comments with page {}", pageable.toString());

        return commentMapper.entityToResponsePage(
                commentService.findAll(pageable)
        );
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete existing comment", description = "Delete existing comment.")
    public void delete(@PathVariable String id) {
        log.info("DELETE comment with id {}", id);

        commentService
                .findById(id)
                .ifPresentOrElse(commentService::delete,
                        () -> {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment with id " + id + " not found!");
                        });
    }
}
