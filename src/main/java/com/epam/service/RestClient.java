package com.epam.service;

import io.restassured.http.ContentType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static io.restassured.RestAssured.given;

public class RestClient {
    private static final Logger LOGGER = LogManager.getLogger(RestClient.class);
    private final String rpToken;


    public RestClient() {
        PropertyHolderService propertyHolderService = new PropertyHolderService();
        this.rpToken = propertyHolderService.getOrSystem("rp.token", "RP_TOKEN");
    }

    public <T> T get(String url, Class<T> clazz) {
        return given()
                .header("Authorization", "bearer " + rpToken)
                .get(url)
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .extract()
                .as(clazz);
    }

    public int get(String url) {
        return given()
                .header("Authorization", "bearer " + rpToken)
                .get(url)
                .then()
                .extract()
                .statusCode();
    }

    public <T> T post(String url, Object body, Class<T> clazz) {
        LOGGER.info("URL={}, token={}, body={}", url, rpToken, body);
        return given()
                .header("Authorization", "bearer " + rpToken)
                .contentType(ContentType.JSON)
                .body(body)
                .post(url)
                .then()
                .assertThat()
                .statusCode(201)
                .and()
                .extract()
                .as(clazz);
    }

    public <T> T put(String url, Object body, Class<T> clazz, int statusCode) {
        return given()
                .header("Authorization", "bearer " + rpToken)
                .contentType(ContentType.JSON)
                .body(body)
                .put(url)
                .then()
                .assertThat()
                .statusCode(statusCode)
                .extract()
                .as(clazz);
    }

    public <T> T delete(String url, Class<T> clazz) {
        return given()
                .header("Authorization", "bearer " + rpToken)
                .contentType(ContentType.JSON)
                .delete(url)
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .extract()
                .as(clazz);
    }
}
