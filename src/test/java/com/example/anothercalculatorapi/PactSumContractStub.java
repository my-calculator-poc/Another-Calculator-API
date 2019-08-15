package com.example.anothercalculatorapi;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import com.example.anothercalculatorapi.model.SumResponse;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/*****
 * mvn pact:publish
 */


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class PactSumContractStub {
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
        String jsonBody = "{ \"number1\": 1, \"number2\": 4 }";

        // when
        ResponseEntity<SumResponse> postResponse = new RestTemplate().exchange(mockSumProvider.getUrl() + "/sum", HttpMethod.POST, new HttpEntity<>(jsonBody, httpHeaders), SumResponse.class);

        // then
        assertThat(postResponse.getStatusCode().value()).isEqualTo(200);
        assertThat(postResponse.getBody().getResult()).isEqualTo(5);
    }
}
