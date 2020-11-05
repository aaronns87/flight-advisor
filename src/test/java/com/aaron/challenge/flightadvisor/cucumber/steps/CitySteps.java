package com.aaron.challenge.flightadvisor.cucumber.steps;

import com.aaron.challenge.flightadvisor.cucumber.clients.CityRestClient;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.ValidatableResponse;
import org.springframework.beans.factory.annotation.Autowired;

import javax.json.Json;

import static org.hamcrest.Matchers.*;

public class CitySteps {

    @Autowired
    private CityRestClient cityRestClient;

    private ValidatableResponse validatableResponse;

    @When("City is created with name {string} in country {string}")
    public void cityIsCreated(String name, String country) {
        var body = Json.createObjectBuilder()
                .add("name", name)
                .add("country", country)
                .build();

        validatableResponse = cityRestClient.create(body);
    }

    @Then("City request is performed with status code (.*)")
    public void cityRequestStatusCode(int statusCode) {
        validatableResponse.statusCode(statusCode);
    }

    @Then("City exists with name {string} in country {string}")
    public void cityExists(String name, String country) {
        cityRestClient.findAll()
                .body("content.id", notNullValue())
                .body("content.name", contains(name))
                .body("content.country", contains(country));
    }

    @Then("City with name {string} is found in country {string}")
    public void searchCity(String name, String country) {
        var body = Json.createObjectBuilder()
                .add("name", name)
                .build();

        cityRestClient.search(body)
                .body("content.country", contains(country));
    }

    @Then("City with name {string} has comment on index {string} with description {string}")
    public void searchCityComments(String name, String index, String description) {
        var body = Json.createObjectBuilder()
                .add("name", name)
                .build();

        cityRestClient.search(body)
                .body("content.comments[0].description[" + Integer.parseInt(index) + "]", equalTo(description));
    }
}
