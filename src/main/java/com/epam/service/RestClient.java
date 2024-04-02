package com.epam.service;

import io.restassured.http.ContentType;

import java.util.Map;

import static com.epam.constant.Constants.CREATE_TOKEN;
import static io.restassured.RestAssured.given;

public class RestClient {
    private final String userName;
    private final String password;
    private final String rpToken;
    private final String baseUrl;

    public RestClient() {
        PropertyHolderService propertyHolderService = new PropertyHolderService();
        this.userName = propertyHolderService.getOrSystem("rp.user-name", "ADMIN");
        this.password = propertyHolderService.getOrSystem("rp.password", "PASSWORD");
        this.rpToken = propertyHolderService.getOrSystem("rp.token", "RP_TOKEN");
        this.baseUrl = propertyHolderService.getOrSystem("rp.endpoint", "BASE_URL") + "/%s";
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

    //should return "access_token" according to ReportPortal docs - https://reportportal.io/docs/reportportal-configuration/HowToGetAnAccessTOKENInReportPortal#1-authorization-with-users-login-and-password
    //but returns 500 Internal Server Error with message "Unclassified error [could not extract ResultSet]", that's why workaround provides into README file
    private String getToken() {
        return given()
                .relaxedHTTPSValidation()
                .body(Map.of("grant_type", "password", "username", userName, "password", password))
                .post(String.format(baseUrl, CREATE_TOKEN))
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath().getString("access_token");
    }
}
