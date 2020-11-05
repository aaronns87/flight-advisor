package com.aaron.challenge.flightadvisor.cities.web;

import com.aaron.challenge.flightadvisor.comments.web.CommentResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

import java.util.List;

@Getter
@Setter
@Builder
public class CityResponse {

    @Schema(description = "City id")
    private String id;

    @Schema(description = "Name", example = "London")
    private String name;

    @Schema(description = "Country", example = "England")
    private String country;

    @Singular
    @Schema(description = "Comments")
    private List<CommentResponse> comments;
}
