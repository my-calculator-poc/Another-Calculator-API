package com.example.anothercalculatorapi;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import com.example.anothercalculatorapi.model.SumRequest;
import com.example.anothercalculatorapi.model.SumResponse;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static com.example.anothercalculatorapi.Util.getMessageConverters;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestPropertySource(locations = "classpath:pact-application.properties")
public class PactSumContractStub {
    @Autowired
    private ApplicationProperties applicationProperties;

    @Rule
    public PactProviderRuleMk2 mockSumProvider = new PactProviderRuleMk2("sum_provider", "localhost", 8280, this);

    @Pact(consumer = "another_calculator_api")
    public RequestResponsePact createSumPact(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        return builder
                .given("test sum POST")
                .uponReceiving("Sum POST REQUEST").method("POST").headers(headers).body("{ \"number1\": 1, \"number2\": 4 }").path("/sum")
                .willRespondWith().body("5").status(200).toPact();
    }

    @Test
    @PactVerification()
    public void sum_1_and_4_happy_flow() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(getMessageConverters());

        // when
        HttpEntity<SumRequest> request = new HttpEntity<>(new SumRequest(1, 4), httpHeaders);
        SumResponse sumResponse = restTemplate.postForObject(mockSumProvider.getUrl() + "/sum", request, SumResponse.class);

        // then
        assertThat(sumResponse.getResult()).isEqualTo(5);
    }

    @Test
    @PactVerification()
    public void end2end_sum_1_and_4_happy_flow() {

        given().standaloneSetup(new CalculatorController(applicationProperties)).
                when().get("/calc?number1=1&number2=4&op=sum").
                then().statusCode(200).
                body("result", equalTo(5));
    }
}
