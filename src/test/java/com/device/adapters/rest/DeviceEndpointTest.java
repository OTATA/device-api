package com.device.adapters.rest;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.micronaut.test.support.TestPropertyProvider;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Map;
import java.util.UUID;

@MicronautTest
@Testcontainers(disabledWithoutDocker = true)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DeviceEndpointTest implements TestPropertyProvider {

    @Container
    private static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:6")).withExposedPorts(27017);

    @Test public void allFlowHappyPath(RequestSpecification spec) {

        String externalKey = spec.when()
                .contentType("application/json")
                .body("{\"brand\": \"HomeInc\", \"name\": \"First Device\"}")
                .post("/v1/devices")
                .then()
                .statusCode(201)
                .body("brand", Matchers.equalTo("HomeInc"))
                .body("name", Matchers.equalTo("First Device"))
                .extract().body().jsonPath().get("externalKey");

        spec.when().get("/v1/devices")
                .then()
                .statusCode(200)
                .body("size()", Matchers.equalTo(1));

        spec.when().get("/v1/devices/" + UUID.fromString(externalKey))
                .then()
                .statusCode(200)
                .body("brand", Matchers.equalTo("HomeInc"))
                .body("name", Matchers.equalTo("First Device"));

        spec.when().get("/v1/devices/brands/HomeInc")
                .then()
                .statusCode(200)
                .body("size()", Matchers.equalTo(1));

        spec.when()
                .contentType("application/json")
                .body("{\"brand\": \"HomeInc Updated\", \"name\": \"First Device Updated\"}")
                .put("/v1/devices/" + externalKey)
                .then()
                .statusCode(200)
                .body("brand", Matchers.equalTo("HomeInc Updated"))
                .body("name", Matchers.equalTo("First Device Updated"));

        spec.when()
                .contentType("application/json")
                .body("{\"brand\": \"HomeInc Updated 2\", \"name\": \"First Device Updated 2\"}")
                .patch("/v1/devices/" + externalKey)
                .then()
                .statusCode(200)
                .body("brand", Matchers.equalTo("HomeInc Updated 2"))
                .body("name", Matchers.equalTo("First Device Updated 2"));

        spec.when()
                .contentType("application/json")
                .body("{\"brand\": \"HomeInc Updated 3\"}")
                .patch("/v1/devices/" + externalKey)
                .then()
                .statusCode(200)
                .body("brand", Matchers.equalTo("HomeInc Updated 3"))
                .body("name", Matchers.equalTo("First Device Updated 2"));

        spec.when()
                .delete("/v1/devices/" + externalKey)
                .then()
                .statusCode(204);
    }

    @Test public void notFoundDevice(RequestSpecification spec) {
        spec.when().get("/v1/devices/" + UUID.randomUUID())
                .then()
                .statusCode(404);
    }

    @Test public void deviceWithInvalidData(RequestSpecification spec) {
        spec.when()
                .contentType("application/json")
                .body("{\"brand\": \"\", \"name\": \"B\"}")
                .post("/v1/devices")
                .then()
                .statusCode(400)
                .body("message", Matchers.equalTo("Invalid device"))
                .body("errors[0]", Matchers.equalTo("Invalid brand"))
                .body("errors[1]", Matchers.equalTo("Invalid name"));
    }

    @Override
    public @NonNull Map<String, String> getProperties() {
        mongoDBContainer.start();
        return Map.of("mongodb.uri", mongoDBContainer.getReplicaSetUrl());
    }
}