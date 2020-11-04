package com.aaron.challenge.flightadvisor.cucumber.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Component
public class DbUtils {

    private static final List<String> TABLES = Arrays.asList(
            "route",
            "airline",
            "airport",
            "comment",
            "city",
            "\"user\"");

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void cleanDB() {
        Stream.of(TABLES)
                .flatMap(Collection::stream)
                .forEach(this::truncateTable);
    }

    private void truncateTable(String name) {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, name);
    }
}
