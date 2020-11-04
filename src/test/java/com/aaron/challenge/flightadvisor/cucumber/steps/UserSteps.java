package com.aaron.challenge.flightadvisor.cucumber.steps;

import com.aaron.challenge.flightadvisor.cucumber.SpringConfiguration;
import com.aaron.challenge.flightadvisor.cucumber.clients.UserRestClient;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.ValidatableResponse;
import org.springframework.beans.factory.annotation.Autowired;

import javax.json.Json;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.notNullValue;

public class UserSteps extends SpringConfiguration {

    @Autowired
    private UserRestClient userRestClient;

    private ValidatableResponse validatableResponse;

    @When("User is created with role {string}, first name {string}, last name {string}, username {string}, password {string}")
    public void cityIsCreated(String role,
                              String firstName,
                              String lastName,
                              String userName,
                              String password) {
        var body = Json.createObjectBuilder()
                .add("role", role)
                .add("firstName", firstName)
                .add("lastName", lastName)
                .add("userName", userName)
                .add("password", password)
                .build();

        validatableResponse = userRestClient.create(body);
    }

    @Then("User request is performed with status code (.*)")
    public void userRequestStatusCode(int statusCode) {
        validatableResponse.statusCode(statusCode);
    }

    @Then("User exists with role {string}, first name {string}, last name {string}, username {string}")
    public void userExists(String role,
                           String firstName,
                           String lastName,
                           String userName) {

        userRestClient.findAll()
                .body("content.id", notNullValue())
                .body("content.firstName", contains(firstName))
                .body("content.lastName", contains(lastName))
                .body("content.userName", contains(userName))
                .body("content.role", contains(role));
    }

}
