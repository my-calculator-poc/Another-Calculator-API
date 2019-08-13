package com.example.anothercalculatorapi;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
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

Ø
        Ø

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class PactContractStubs {
    @Rule
    public PactProviderRuleMk2 mockProvider
            = new PactProviderRuleMk2("test_provider", "localhost", 8080, this);

    @Pact(consumer = "test_consumer")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        return builder
                .given("test multiply POST")
                .uponReceiving("Multiply POST REQUEST").method("POST").headers(headers).body("{ \"number1\": 2, \"number2\": 3 }").path("/multiply")
                .willRespondWith().body("6").status(200).toPact();
    }

    @Test
    @PactVerification()
    public void givenGet_whenSendRequest_shouldReturn200WithProperHeaderAndBody() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        String jsonBody = "{ \"number1\": 2, \"number2\": 3 }";

        // when
        ResponseEntity<String> postResponse = new RestTemplate().exchange(mockProvider.getUrl() + "/multiply", HttpMethod.POST, new HttpEntity<>(jsonBody, httpHeaders), String.class);

        // then
        assertThat(postResponse.getStatusCode().value()).isEqualTo(200);
    }
}
