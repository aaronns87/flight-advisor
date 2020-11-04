package com.aaron.challenge.flightadvisor.users.web;

import com.aaron.challenge.flightadvisor.users.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserUpdate {

    @Schema(description = "User role", example = "ADMIN")
    private Role role;

    @Schema(description = "First name", example = "John")
    private String firstName;

    @Schema(description = "Last name", example = "Doe")
    private String lastName;

    @Schema(description = "User name", example = "john.doe")
    private String userName;

    @Schema(description = "Password")
    private String password;
}
