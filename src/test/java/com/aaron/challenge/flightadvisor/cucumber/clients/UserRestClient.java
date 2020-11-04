package com.aaron.challenge.flightadvisor.cucumber.clients;

import org.springframework.stereotype.Component;

@Component
public class UserRestClient implements BaseRestClient {

    private static final String BASE_PATH = "/admin/users";

    @Override
    public String getBasePath() {
        return BASE_PATH;
    }
}
