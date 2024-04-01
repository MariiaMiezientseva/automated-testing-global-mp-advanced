package com.epam.service;

import io.restassured.http.ContentType;

import java.io.File;
import java.util.Map;

import static com.epam.constant.Constants.*;
import static io.restassured.RestAssured.given;

public class RestClient {
    private final static String token = System.getenv(TOKEN);

    public <T> T get(String url, Class<T> clazz) {
        return given()
                .header("Authorization", "bearer " + token)
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
                .header("Authorization", "bearer " + token)
                .get(url)
                .then()
                .extract()
                .statusCode();
    }

    public <T> T post(String url, String bodyPath, Class<T> clazz) {
        return given()
                .header("Authorization", "bearer " + token)
                .contentType(ContentType.JSON)
                .body(new File(bodyPath))
                .post(url)
                .then()
                .assertThat()
                .statusCode(201)
                .and()
                .extract()
                .as(clazz);
    }

    public <T> T put(String url, File body, Class<T> clazz, int statusCode) {
        return given()
                .header("Authorization", "bearer " + token)
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
                .header("Authorization", "bearer " + token)
                .contentType(ContentType.JSON)
                .delete(url)
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .extract()
                .as(clazz);
    }

    //should return "access_token" according to ReportPortal docs - https://reportportal.io/docs/reportportal-configuration/HowToGetAnAccessTokenInReportPortal#1-authorization-with-users-login-and-password
    //but returns 401 Unauthorized, that's why workaround provides into README file
    private String getToken() {
        return given()
                .relaxedHTTPSValidation()
                .body(Map.of("grant_type", "password", "username", System.getenv(ADMIN), "password", System.getenv(PASSWORD)))
                .post(String.format(BASE_URL, CREATE_TOKEN))
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath().getString("access_token");
    }
}