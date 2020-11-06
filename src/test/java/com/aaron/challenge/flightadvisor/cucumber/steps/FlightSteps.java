package com.aaron.challenge.flightadvisor.cucumber.steps;

import com.aaron.challenge.flightadvisor.cucumber.clients.FlightRestClient;
import cucumber.api.java.en.When;
import io.restassured.response.ValidatableResponse;
import org.springframework.beans.factory.annotation.Autowired;

public class FlightSteps {

    @Autowired
    private FlightRestClient flightRestClient;

    private ValidatableResponse validatableResponse;

    @When("Cheapest flight is searched for source city {string}, country {string}, destination city {string}, country {string}")
    public void searchCheapest(String sourceCityName, String sourceCityCountry,
                               String destinationCityName, String destinationCityCountry) {
        validatableResponse = flightRestClient.searchCheapest(
                sourceCityName,
                sourceCityCountry,
                destinationCityName,
                destinationCityCountry
        );
    }
}
