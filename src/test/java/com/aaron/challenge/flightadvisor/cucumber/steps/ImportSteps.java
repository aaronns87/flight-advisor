package com.aaron.challenge.flightadvisor.cucumber.steps;

import com.aaron.challenge.flightadvisor.cucumber.clients.ImportRestClient;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.ValidatableResponse;
import org.springframework.beans.factory.annotation.Autowired;

public class ImportSteps {

    @Autowired
    private ImportRestClient importRestClient;

    private ValidatableResponse validatableResponse;

    @When("Airports are imported with content {string}")
    public void airportImport(String content) {
        validatableResponse = importRestClient.importAirports(content);
    }

    @When("Routes are imported with content {string}")
    public void routeImport(String content) {
        validatableResponse = importRestClient.importRoutes(content);
    }

    @Then("Import request is performed with status code (.*)")
    public void routeRequestStatusCode(int statusCode) {
        validatableResponse.statusCode(statusCode);
    }

}
