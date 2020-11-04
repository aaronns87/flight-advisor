package com.aaron.challenge.flightadvisor.cucumber.clients;

import org.springframework.stereotype.Component;

@Component
public class CommentRestClient implements BaseRestClient {

    private static final String BASE_PATH = "/comments";

    @Override
    public String getBasePath() {
        return BASE_PATH;
    }
}
