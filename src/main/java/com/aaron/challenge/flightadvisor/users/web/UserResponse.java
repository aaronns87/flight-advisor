package com.aaron.challenge.flightadvisor.users.web;

import com.aaron.challenge.flightadvisor.users.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserResponse {

    @Schema(description = "User id", example = "1")
    private Long id;

    @Schema(description = "User role", example = "ADMIN")
    private Role role;

    @Schema(description = "First name", example = "John")
    private String firstName;

    @Schema(description = "Last name", example = "Doe")
    private String lastName;

    @Schema(description = "User name", example = "john.doe")
    private String userName;
}
