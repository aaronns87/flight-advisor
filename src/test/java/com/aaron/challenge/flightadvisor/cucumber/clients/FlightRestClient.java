package com.aaron.challenge.flightadvisor.cucumber.clients;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.springframework.stereotype.Component;

import javax.json.Json;

@Component
public class FlightRestClient {

    private static final String BASE_PATH = "/flights";

    public ValidatableResponse searchCheapest(String sourceCityName, String sourceCityCountry,
                                              String destinationCityName, String destinationCityCountry) {

        var body = Json.createObjectBuilder()
                .add("source", Json.createObjectBuilder()
                        .add("name", sourceCityName)
                        .add("country", sourceCityCountry)
                        .build())
                .add("destination", Json.createObjectBuilder()
                        .add("name", destinationCityName)
                        .add("country", destinationCityCountry)
                        .build())
                .build();

        return RestAssured.given()
                .body(body.toString())
                .when()
                .contentType(ContentType.JSON)
                .get(BASE_PATH + "/cheapest")
                .then();
    }
}
