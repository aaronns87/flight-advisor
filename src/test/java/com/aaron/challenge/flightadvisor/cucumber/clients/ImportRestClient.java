package com.aaron.challenge.flightadvisor.cucumber.clients;

import io.restassured.RestAssured;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.MultiPartSpecification;
import org.springframework.stereotype.Component;

@Component
public class ImportRestClient implements BaseRestClient {

    private static final String BASE_PATH = "/admin/import";

    public ValidatableResponse importAirports(String content) {
        return RestAssured.given()
                .multiPart(getMultiPart(content, "airports.csv"))
                .when()
                .post(getBasePath() + "/airports")
                .then();
    }

    public ValidatableResponse importRoutes(String content) {
        return RestAssured.given()
                .multiPart(getMultiPart(content, "routes.csv"))
                .when()
                .post(getBasePath() + "/routes")
                .then();
    }

    @Override
    public String getBasePath() {
        return BASE_PATH;
    }

    private MultiPartSpecification getMultiPart(String content, String fileName) {
        return new MultiPartSpecBuilder(content.getBytes()).
                fileName(fileName).
                controlName("file").
                mimeType("text/csv").
                build();
    }
}
