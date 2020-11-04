package com.aaron.challenge.flightadvisor.cucumber.clients;

import org.springframework.stereotype.Component;

import javax.json.Json;

@Component
public class CityRestClient implements BaseRestClient {

    private static final String BASE_PATH = "/admin/cities";

    public String findIdByName(String name) {
        var body = Json.createObjectBuilder()
                .add("name", name)
                .build();

        return findId(body);
    }

    @Override
    public String getBasePath() {
        return BASE_PATH;
    }
}
