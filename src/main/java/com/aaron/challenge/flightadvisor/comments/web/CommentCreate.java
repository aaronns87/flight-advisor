package com.aaron.challenge.flightadvisor.comments.web;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class CommentCreate {

    @NotBlank
    @Schema(description = "City Id")
    private String cityId;

    @NotBlank
    @Schema(description = "Description", example = "No flights due to bad weather.")
    private String description;
}
