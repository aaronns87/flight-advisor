package com.aaron.challenge.flightadvisor.airports;

public enum DST {
    E("Europe"),
    A("US/Canada"),
    S("South America"),
    O("Australia"),
    Z("New Zealand"),
    N("None"),
    U("Unknown");

    String name;

    DST(String name) {
        this.name = name;
    }
}
