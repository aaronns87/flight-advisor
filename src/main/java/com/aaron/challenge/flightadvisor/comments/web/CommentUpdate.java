package com.aaron.challenge.flightadvisor.comments.web;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommentUpdate {

    @Schema(description = "Description", example = "No flights due to bad weather.")
    private String description;
}
