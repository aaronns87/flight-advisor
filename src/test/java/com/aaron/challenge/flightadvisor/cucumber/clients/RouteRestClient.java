package com.aaron.challenge.flightadvisor.cucumber.clients;

import org.springframework.stereotype.Component;

@Component
public class RouteRestClient implements BaseRestClient {

    private static final String BASE_PATH = "/admin/routes";

    @Override
    public String getBasePath() {
        return BASE_PATH;
    }
}
