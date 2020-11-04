package com.aaron.challenge.flightadvisor.cucumber;

import com.aaron.challenge.flightadvisor.cucumber.utils.DbUtils;
import cucumber.api.java.Before;
import io.restassured.RestAssured;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;

public final class Hooks {

    @LocalServerPort
    protected int port;

    @Autowired
    private DbUtils dbUtils;

    @Before
    public void init() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        cleanAndSetup();
    }

    private void cleanAndSetup() {
        dbUtils.cleanDB();
    }
}
