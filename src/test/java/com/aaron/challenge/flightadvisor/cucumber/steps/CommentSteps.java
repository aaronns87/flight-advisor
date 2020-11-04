package com.aaron.challenge.flightadvisor.cucumber.steps;

import com.aaron.challenge.flightadvisor.cucumber.clients.CityRestClient;
import com.aaron.challenge.flightadvisor.cucumber.clients.CommentRestClient;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.ValidatableResponse;
import org.springframework.beans.factory.annotation.Autowired;

import javax.json.Json;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.notNullValue;

public class CommentSteps {

    @Autowired
    private CommentRestClient commentRestClient;

    @Autowired
    private CityRestClient cityRestClient;

    private ValidatableResponse validatableResponse;

    @When("Comment is created for city name {string}, with description {string}")
    public void commentIsCreated(String cityName, String description) {
        var cityId = cityRestClient.findIdByName(cityName);

        var body = Json.createObjectBuilder()
                .add("cityId", cityId)
                .add("description", description)
                .build();

        validatableResponse = commentRestClient.create(body);
    }

    @Then("Comment request is performed with status code (.*)")
    public void commentRequestStatusCode(int statusCode) {
        validatableResponse.statusCode(statusCode);
    }

    @Then("Comment exists for city name {string}, with description {string}")
    public void commentExists(String cityName, String description) {
        commentRestClient.findAll()
                .body("content.id", notNullValue())
                .body("content.city.name", contains(cityName))
                .body("content.description", contains(description))
                .body("content.createdDate", notNullValue())
                .body("content.modifiedDate", notNullValue());
    }
}
