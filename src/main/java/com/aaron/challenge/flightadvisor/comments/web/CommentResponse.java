package com.aaron.challenge.flightadvisor.comments.web;

import com.aaron.challenge.flightadvisor.cities.web.CityResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CommentResponse {

    @Schema(description = "Comment id")
    private String id;

    @Schema(description = "City", example = "New York")
    private CityResponse city;

    @Schema(description = "Description", example = "No flights due to bad weather.")
    private String description;

    @Schema(description = "Created date")
    private LocalDateTime createdDate;

    @Schema(description = "Modified date")
    private LocalDateTime modifiedDate;
}
