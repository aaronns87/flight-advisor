package com.aaron.challenge.flightadvisor.cucumber.steps;

import com.aaron.challenge.flightadvisor.cucumber.clients.AirlineRestClient;
import com.aaron.challenge.flightadvisor.cucumber.clients.AirportRestClient;
import com.aaron.challenge.flightadvisor.cucumber.clients.RouteRestClient;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.ValidatableResponse;
import org.springframework.beans.factory.annotation.Autowired;

import javax.json.Json;

import static org.hamcrest.Matchers.*;

public class RouteSteps {

    @Autowired
    private RouteRestClient routeRestClient;

    @Autowired
    private AirlineRestClient airlineRestClient;

    @Autowired
    private AirportRestClient airportRestClient;

    private ValidatableResponse validatableResponse;

    @When("Route is created for airline code {string}, source airport code {string}, source airport name {string}, destination airport code {string}, destination airport name {string}, code share {string}, stops {string}, equipment {string}, price {string}")
    public void routeIsCreated(String airlineCode,
                               String sourceAirportCode,
                               String sourceAirportName,
                               String destinationAirportCode,
                               String destinationAirportName,
                               String codeShare,
                               String stops,
                               String equipment,
                               String price) {

        var airlineId = airlineRestClient.findIdByCode(airlineCode);

        var sourceAirportId = airportRestClient.findIdByName(sourceAirportName);
        var destinationAirportId = airportRestClient.findIdByName(destinationAirportName);

        var body = Json.createObjectBuilder()
                .add("airlineCode", airlineCode)
                .add("airlineId", airlineId)
                .add("sourceAirportCode", sourceAirportCode)
                .add("sourceAirportId", sourceAirportId)
                .add("destinationAirportCode", destinationAirportCode)
                .add("destinationAirportId", destinationAirportId)
                .add("codeShare", Boolean.parseBoolean(codeShare))
                .add("stops", Integer.parseInt(stops))
                .add("equipment", equipment)
                .add("price", Float.parseFloat(price))
                .build();

        validatableResponse = routeRestClient.create(body);
    }

    @Then("Route request is performed with status code (.*)")
    public void routeRequestStatusCode(int statusCode) {
        validatableResponse.statusCode(statusCode);
    }

    @Then("Route exists for airline code {string}, source airport code {string}, source airport name {string}, destination airport code {string}, destination airport name {string}, code share {string}, stops {string}, equipment {string}, price {string}")
    public void routeExists(String airlineCode,
                            String sourceAirportCode,
                            String sourceAirportName,
                            String destinationAirportCode,
                            String destinationAirportName,
                            String codeShare,
                            String stops,
                            String equipment,
                            String price) {
        routeRestClient.findAll()
                .body("content.id", notNullValue())
                .body("content.airlineCode", contains(airlineCode))
                .body("content.airline.code", contains(airlineCode))
                .body("content.sourceAirportCode", contains(sourceAirportCode))
                .body("content.sourceAirport.name", contains(sourceAirportName))
                .body("content.destinationAirportCode", contains(destinationAirportCode))
                .body("content.destinationAirport.name", contains(destinationAirportName))
                .body("content.codeShare[0]", is(Boolean.parseBoolean(codeShare)))
                .body("content.stops[0]", equalTo(Integer.parseInt(stops)))
                .body("content.equipment", contains(equipment))
                .body("content.price[0]", equalTo(Float.parseFloat(price)));
    }
}
