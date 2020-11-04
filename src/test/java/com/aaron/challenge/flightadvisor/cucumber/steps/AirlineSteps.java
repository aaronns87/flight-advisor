package com.aaron.challenge.flightadvisor.cucumber.steps;

import com.aaron.challenge.flightadvisor.cucumber.clients.AirlineRestClient;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.ValidatableResponse;
import org.springframework.beans.factory.annotation.Autowired;

import javax.json.Json;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.notNullValue;

public class AirlineSteps {

    @Autowired
    private AirlineRestClient airlineRestClient;

    private ValidatableResponse validatableResponse;

    @When("Airline is created with code {string}")
    public void airlineIsCreated(String code) {
        var body = Json.createObjectBuilder()
                .add("code", code)
                .build();

        validatableResponse = airlineRestClient.create(body);
    }

    @Then("Airline request is performed with status code (.*)")
    public void cityRequestStatusCode(int statusCode) {
        validatableResponse.statusCode(statusCode);
    }

    @Then("Airline exists with code {string}")
    public void airlineExists(String code) {
        airlineRestClient.findAll()
                .body("content.id", notNullValue())
                .body("content.code", contains(code));
    }
}
