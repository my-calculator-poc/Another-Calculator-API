package com.example.anothercalculatorapi;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/* This test class simulates a connected integration test
 * To run it download and run:
 * git clone https://github.com/my-calculator-poc/My-Multiply-API
 * mvn spring-boot:run
 *
 * And in another terminal:
 * git clone https://github.com/my-calculator-poc/My-Sum-API.git
 * mvn spring-boot:run
 *
 * And comment out or remove the @ignore annotations
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)

public class AnotherCalculatorApiApplicationTests {

    @Autowired
    private ApplicationProperties applicationProperties;

	@Test
	@Ignore
	public void sum_1_and2_happy_flow() {
        given().standaloneSetup(new CalculatorController(applicationProperties)).
				when().	get("/calc?number1=1&number2=2&op=sum").
				then().	statusCode(200).
				body("result",equalTo(3));
	}

	@Test
	@Ignore
	public void multiply_1_and2_happy_flow() {
        given().standaloneSetup(new CalculatorController(applicationProperties)).
				when().	get("/calc?number1=1&number2=2&op=mult").
				then().	statusCode(200).
				body("result",equalTo(2));

    }
}
