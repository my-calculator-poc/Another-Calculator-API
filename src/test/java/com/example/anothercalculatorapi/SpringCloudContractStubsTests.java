package com.example.anothercalculatorapi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:spring-application.properties")
@AutoConfigureMockMvc
@AutoConfigureStubRunner(
        ids = {"org.jab.microservices:my-sum:0.1.0-SNAPSHOT:stubs:8180",
                "com.example:My-Multiply-API:0.0.1-SNAPSHOT:stubs:8181"},
        stubsMode = StubRunnerProperties.StubsMode.LOCAL)
public class SpringCloudContractStubsTests {

    @Autowired
    private ApplicationProperties applicationProperties;

    @Test
    public void sum_1_and_3_happy_flow() {
        given().standaloneSetup(new CalculatorController(applicationProperties)).
                when().get("/calc?number1=1&number2=3&op=sum").
                then().statusCode(200).
                body("result", equalTo(4));
    }

    @Test
    public void multiply_2_and_3_happy_flow() {
        given().standaloneSetup(new CalculatorController(applicationProperties)).
                when().get("/calc?number1=2&number2=3&op=mult").
                then().statusCode(200).
                body("result", equalTo(6));

    }

}
