package com.example.ea_countries_stats.domain.country;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/test-data/cleanup.sql")
@Sql("/test-data/base-data.sql")
public class CountryIntegrationTest {

    private final static String BASE_URI = "http://localhost";

    @LocalServerPort
    private int port;

    @BeforeEach
    public void configureRestAssured() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;
    }

    @Test
    public void testGetAllCountries() {
        when()
                .get("/countries")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("content.find { it.countryTxt == 'USA' }.attacks", not(empty()));
    }
    @Test
    public void testGetCountryById() {
        when()
                .get("/countries/2")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("content.countryId", is(2))
                .body("content.countryTxt", is("Czechia"));
    }

    @Test
    public void testGetCountryById_NotFound() {
        when()
                .get("/countries/9999")
                .then()
                .statusCode(404);
    }

    @Test
    public void testGetCountriesProbabilities() {
        when()
                .get("/countries/probabilites")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("items.find { it.countryTxt == 'USA' }.probability", equalTo(0.5f));
    }
}
