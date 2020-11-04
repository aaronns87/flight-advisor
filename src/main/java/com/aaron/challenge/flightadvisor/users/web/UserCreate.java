package com.aaron.challenge.flightadvisor.users.web;

import com.aaron.challenge.flightadvisor.users.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class UserCreate {

    @NotBlank
    @Schema(description = "User role", example = "ADMIN")
    private Role role;

    @NotBlank
    @Schema(description = "First name", example = "John")
    private String firstName;

    @NotBlank
    @Schema(description = "Last name", example = "Doe")
    private String lastName;

    @NotBlank
    @Schema(description = "User name", example = "john.doe")
    private String userName;

    @NotBlank
    @Schema(description = "Password")
    private String password;
}
