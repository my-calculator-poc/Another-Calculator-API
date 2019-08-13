package com.example.anothercalculatorapi;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static wiremock.org.hamcrest.core.Is.is;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class AnotherCalculatorApiApplicationTests {

	@Test
	@Ignore
	public void sum_1_and2_happy_flow() {
		given().standaloneSetup(new CalculatorController()).
				when().	get("/calc?number1=1&number2=2&op=sum").
				then().	statusCode(200).
				body("result",equalTo(3));
	}

	@Test
	@Ignore
	public void multiply_1_and2_happy_flow() {
		given().standaloneSetup(new CalculatorController()).
				when().	get("/calc?number1=1&number2=2&op=mult").
				then().	statusCode(200).
				body("result",equalTo(2));
	}
}
