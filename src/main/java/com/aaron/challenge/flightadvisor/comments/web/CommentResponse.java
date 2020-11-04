package com.aaron.challenge.flightadvisor.comments.web;

import com.aaron.challenge.flightadvisor.cities.City;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CommentResponse {

    @Schema(description = "Comment id", example = "1")
    private Long id;

    @Schema(description = "City", example = "New York")
    private City city;

    @Schema(description = "Description", example = "No flights due to bad weather.")
    private String description;

    @Schema(description = "Created date")
    private LocalDateTime createdDate;

    @Schema(description = "Modified date")
    private LocalDateTime modifiedDate;
}
