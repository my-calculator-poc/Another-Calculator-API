package com.example.anothercalculatorapi;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/*****
 * mvn pact:publish
 */


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestPropertySource(locations = "classpath:pact-application.properties")
public class PactMultiplyContractStub {
    @Autowired
    private ApplicationProperties applicationProperties;

    @Rule
    public PactProviderRuleMk2 mockMultiplyProvider = new PactProviderRuleMk2("multiply_provider", "localhost", 8281, this);

    @Pact(consumer = "another_calculator_api")
    public RequestResponsePact createMultiplyPact(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        return builder
                .given("test multiply POST")
                .uponReceiving("Multiply POST REQUEST").method("POST").headers(headers).body("{ \"number1\": 2, \"number2\": 3 }").path("/multiply")
                .willRespondWith().body("6").status(200).toPact();
    }

    @Test
    @PactVerification()
    public void multiply_2_and_3_happy_flow() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        String jsonBody = "{ \"number1\": 2, \"number2\": 3 }";

        // when
        ResponseEntity<String> postResponse = new RestTemplate().exchange(mockMultiplyProvider.getUrl() + "/multiply", HttpMethod.POST, new HttpEntity<>(jsonBody, httpHeaders), String.class);

        // then
        assertThat(postResponse.getStatusCode().value()).isEqualTo(200);
        assertThat(postResponse.getBody().contains("6")).isTrue();
    }

    @Test
    @PactVerification()
    public void end2end_multiply_2_and_3_happy_flow() {

        given().contentType("application/json").
                standaloneSetup(new CalculatorController(applicationProperties)).
                when().get("/calc?number1=2&number2=3&op=mult").
                then().statusCode(200).
                body("result", equalTo(6));

    }
}
