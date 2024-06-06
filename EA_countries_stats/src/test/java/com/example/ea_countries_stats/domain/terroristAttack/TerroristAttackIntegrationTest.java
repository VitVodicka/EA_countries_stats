package com.example.ea_countries_stats.domain.terroristAttack;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/test-data/cleanup.sql")
@Sql("/test-data/base-data.sql")
public class TerroristAttackIntegrationTest {

    private final static String BASE_URI = "http://localhost";

    @LocalServerPort
    private int port;

    @BeforeEach
    public void configureRestAssured() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;
    }

    @Test
    public void testCreateTerroristAttack() {
        var newAttack = new TerroristAttackRequest();
        newAttack.setDate(Timestamp.valueOf("2024-06-06 12:00:00"));
        newAttack.setLocation("Location");
        newAttack.setTarget("Target");
        newAttack.setCasualities(10);
        newAttack.setCountry_id(1L);

        given()
                .contentType(ContentType.JSON)
                .body(newAttack)
                .when()
                .post("/terrorist_attacks")
                .then()
                .statusCode(201);
    }

    @Test
    public void testUpdateTerroristAttack_BadRequest() {
        var updatedAttack = new TerroristAttackRequest(null, null, null, null, null);

        given()
                .contentType(ContentType.JSON)
                .body(updatedAttack)
                .when()
                .put("/terrorist_attacks/1")
                .then()
                .statusCode(400); // Expecting a bad request status code
    }


    @Test
    public void testGetTerroristAttacksByCasualities() {
        when()
                .get("/terrorist_attacks/casualities/lowerorhighercasualities/lower/10/ASC")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("", notNullValue());
    }



    @Test
    public void testGetStandardDeviationFromValue() {
        when()
                .get("/terrorist_attacks/casualities/standarddeviationfromvalue/50.0")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }
}