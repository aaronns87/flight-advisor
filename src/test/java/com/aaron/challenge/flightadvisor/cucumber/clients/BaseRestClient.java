package com.aaron.challenge.flightadvisor.cucumber.clients;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import javax.json.JsonObject;

public interface BaseRestClient {

    default ValidatableResponse create(JsonObject body) {
        return RestAssured.given()
                .body(body.toString())
                .when()
                .contentType(ContentType.JSON)
                .post(getBasePath())
                .then();
    }

    default ValidatableResponse update(Long id, JsonObject body) {
        return RestAssured.given()
                .body(body.toString())
                .when()
                .contentType(ContentType.JSON)
                .patch(getBasePath() + "/" + id)
                .then();
    }

    default ValidatableResponse findAll() {
        return RestAssured.given()
                .when()
                .contentType(ContentType.JSON)
                .get(getBasePath())
                .then();
    }

    default ValidatableResponse search(JsonObject body) {
        return RestAssured.given()
                .body(body.toString())
                .when()
                .contentType(ContentType.JSON)
                .get(getBasePath() + "/search")
                .then();
    }

    default ValidatableResponse delete(Long id, JsonObject body) {
        return RestAssured.given()
                .body(body.toString())
                .when()
                .contentType(ContentType.JSON)
                .delete(getBasePath() + "/" + id)
                .then();
    }

    default Long findId(JsonObject body) {
        return Long.parseLong(
                search(body)
                        .extract()
                        .path("content[0].id")
                        .toString()
        );
    }

    default Long findFirstId() {
        return Long.parseLong(
                findAll()
                        .extract()
                        .path("content[0].id")
                        .toString()
        );
    }

    default int countAll() {
        return Integer.parseInt(
                findAll()
                        .extract()
                        .path("totalElements")
                        .toString()
        );
    }

    String getBasePath();
}
