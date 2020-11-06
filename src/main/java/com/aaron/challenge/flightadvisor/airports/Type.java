package com.aaron.challenge.flightadvisor.airports;

import java.util.Arrays;
import java.util.Optional;

public enum Type {
    AIRPORT("airport"),
    UNKNOWN("unknown");

    String name;

    Type(String name) {
        this.name = name;
    }

    public static Optional<Type> getByName(String name) {
        return Arrays.stream(Type.values())
                .filter(type -> type.getName().equals(name))
                .findFirst();
    }

    String getName() {
        return name;
    }
}
