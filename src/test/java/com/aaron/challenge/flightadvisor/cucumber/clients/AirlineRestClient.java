package com.aaron.challenge.flightadvisor.cucumber.clients;


import org.springframework.stereotype.Component;

import javax.json.Json;

@Component
public class AirlineRestClient implements BaseRestClient {

    private static final String BASE_PATH = "/admin/airlines";

    public Long findIdByCode(String code) {
        var body = Json.createObjectBuilder()
                .add("code", code)
                .build();

        return findId(body);
    }

    @Override
    public String getBasePath() {
        return BASE_PATH;
    }
}
