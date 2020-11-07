package com.aaron.challenge.flightadvisor.cucumber.steps;

import com.aaron.challenge.flightadvisor.cucumber.clients.FlightRestClient;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.ValidatableResponse;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;

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

    @Then("Found (.*) cheapest flight chain options")
    public void cheapestFlightFound(int found) {
        validatableResponse
                .body("found", equalTo(found));
    }

    @Then("Flight chain {string} has total price {string} and hops {string}")
    public void flightChainPriceAndHops(String chainIndex, String totalPrice, String hops) {
        validatableResponse
                .body("chains[" + Integer.parseInt(chainIndex) + "].totalPrice", equalTo(Float.parseFloat(totalPrice)))
                .body("chains[" + Integer.parseInt(chainIndex) + "].hops", equalTo(Integer.parseInt(hops)));

    }

    @Then("Flight chain {string}, flight chain stop {string} airline code is {string} and equipment is {string}")
    public void flightChainStopAirlineCodeEquipment(String chainIndex, String stopIndex, String airlineCode, String equipment) {
        validatableResponse
                .body("chains[" + Integer.parseInt(chainIndex) + "].stops[" + Integer.parseInt(stopIndex) + "].airlineCode", equalTo(airlineCode))
                .body("chains[" + Integer.parseInt(chainIndex) + "].stops[" + Integer.parseInt(stopIndex) + "].equipment", equalTo(equipment));
    }

    @Then("Flight chain {string}, flight chain stop {string} embark city {string}, country {string}, airport {string}, airport code {string} and disembark city {string}, country {string}, airport {string}, airport code {string}")
    public void flightChainStopCities(String chainIndex, String stopIndex, String embarkCity, String embarkCountry, String embarkAirport, String embarkAirportCode, String disembarkCity, String disembarkCountry, String disembarkAirport, String disembarkAirportCode) {
        validatableResponse
                .body("chains[" + Integer.parseInt(chainIndex) + "].stops[" + Integer.parseInt(stopIndex) + "].embark.city", equalTo(embarkCity))
                .body("chains[" + Integer.parseInt(chainIndex) + "].stops[" + Integer.parseInt(stopIndex) + "].embark.country", equalTo(embarkCountry))
                .body("chains[" + Integer.parseInt(chainIndex) + "].stops[" + Integer.parseInt(stopIndex) + "].embark.airport", equalTo(embarkAirport))
                .body("chains[" + Integer.parseInt(chainIndex) + "].stops[" + Integer.parseInt(stopIndex) + "].embark.airportCode", equalTo(embarkAirportCode))
                .body("chains[" + Integer.parseInt(chainIndex) + "].stops[" + Integer.parseInt(stopIndex) + "].disembark.city", equalTo(disembarkCity))
                .body("chains[" + Integer.parseInt(chainIndex) + "].stops[" + Integer.parseInt(stopIndex) + "].disembark.country", equalTo(disembarkCountry))
                .body("chains[" + Integer.parseInt(chainIndex) + "].stops[" + Integer.parseInt(stopIndex) + "].disembark.airport", equalTo(disembarkAirport))
                .body("chains[" + Integer.parseInt(chainIndex) + "].stops[" + Integer.parseInt(stopIndex) + "].disembark.airportCode", equalTo(disembarkAirportCode));

    }
}
