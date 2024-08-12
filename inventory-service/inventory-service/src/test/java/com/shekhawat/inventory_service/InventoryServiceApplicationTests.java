package com.shekhawat.inventory_service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import io.restassured.RestAssured;
import org.testcontainers.containers.MySQLContainer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InventoryServiceApplicationTests {
	@ServiceConnection
	static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.3.0");
	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	static {
		mySQLContainer.start();
	}

	@Test
	void shouldReadInventory() {
		var positiveResponse = RestAssured.given()
				.when()
				.get("/api/inventory?skuCode=iPhone_16&quantity=100")
				.then()
				.log().all()
				.statusCode(200)
				.extract()
				.response().as(Boolean.class);
		assertTrue(positiveResponse);

		var negativeResponse = RestAssured.given()
				.when()
				.get("/api/inventory?skuCode=iPhone_16&quantity=101")
				.then()
				.log().all()
				.statusCode(200)
				.extract()
				.response().as(Boolean.class);
		assertFalse(negativeResponse);
	}

}
