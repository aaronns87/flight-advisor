package com.aaron.challenge.flightadvisor.cucumber.steps;

import com.aaron.challenge.flightadvisor.cucumber.clients.AirportRestClient;
import com.aaron.challenge.flightadvisor.cucumber.clients.CityRestClient;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.ValidatableResponse;
import org.springframework.beans.factory.annotation.Autowired;

import javax.json.Json;

import static org.hamcrest.Matchers.*;

public class AirportSteps {

    @Autowired
    private AirportRestClient airportRestClient;

    @Autowired
    private CityRestClient cityRestClient;

    private ValidatableResponse validatableResponse;

    @When("Airport is created with name {string}, city name {string}, iata {string}, icao {string}, latitude {string}, longitude {string}, altitude {string}, timeZone {string}, dst {string}, tz {string}, type {string}, source {string}")
    public void airportIsCreated(String name,
                                 String cityName,
                                 String iata,
                                 String icao,
                                 String latitude,
                                 String longitude,
                                 String altitude,
                                 String timeZone,
                                 String dst,
                                 String tz,
                                 String type,
                                 String source) {


        var cityId = cityRestClient.findIdByName(cityName);

        var body = Json.createObjectBuilder()
                .add("name", name)
                .add("cityId", cityId)
                .add("iata", iata)
                .add("icao", icao)
                .add("latitude", latitude)
                .add("longitude", longitude)
                .add("altitude", altitude)
                .add("timeZone", timeZone)
                .add("dst", dst)
                .add("tz", tz)
                .add("type", type)
                .add("source", source)
                .build();

        validatableResponse = airportRestClient.create(body);
    }

    @Then("Airport request is performed with status code (.*)")
    public void cityRequestStatusCode(int statusCode) {
        validatableResponse.statusCode(statusCode);
    }

    @Then("Airport exists with name {string}, city name {string}, iata {string}, icao {string}, latitude {string}, longitude {string}, altitude {string}, timeZone {string}, dst {string}, tz {string}, type {string}, source {string}")
    public void cityExists(String name,
                           String cityName,
                           String iata,
                           String icao,
                           String latitude,
                           String longitude,
                           String altitude,
                           String timeZone,
                           String dst,
                           String tz,
                           String type,
                           String source) {

        airportRestClient.findAll()
                .body("content.id", notNullValue())
                .body("content.name", contains(name))
                .body("content.city.name", contains(cityName))
                .body("content.iata", contains(iata))
                .body("content.icao", contains(icao))
                .body("content.latitude", contains(latitude))
                .body("content.longitude", contains(longitude))
                .body("content.altitude[0]", equalTo(Integer.parseInt(altitude)))
                .body("content.timeZone[0]", equalTo(Float.parseFloat(timeZone)))
                .body("content.dst", contains(dst))
                .body("content.tz", contains(tz))
                .body("content.type", contains(type))
                .body("content.source", contains(source));
    }
}
