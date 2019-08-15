package com.example.anothercalculatorapi;

import com.example.anothercalculatorapi.model.MultiplicationRequest;
import com.example.anothercalculatorapi.model.MultiplicationResponse;
import com.example.anothercalculatorapi.model.SumRequest;
import com.example.anothercalculatorapi.model.SumResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController()
public class CalculatorController {

    private final ApplicationProperties applicationProperties;

    @Autowired
    public CalculatorController(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @GetMapping(value = "/calc")
    public ResponseEntity<CalculationResponse> calc(@RequestParam("number1") Integer number1,
                                                    @RequestParam("number2") Integer number2,
                                                    @RequestParam("op") String operation) {

        final String sumResourceUrl = applicationProperties.getSum();
        final String multiplyResourceUrl = applicationProperties.getMultiply();

        // Content-type and Converter needs to be added to solver issue with WireMock: Could not extract response: no suitable HttpMessageConverter found for response type  and content type [application/octet-stream]
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        //Add the Jackson Message converter
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

        // Note: here we are making this converter to process any kind of response,
        // not only application/*json, which is the default behaviour
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverters.add(converter);

        if (operation.contains("sum")) {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.setMessageConverters(messageConverters);

            HttpEntity<SumRequest> request = new HttpEntity<>(new SumRequest(number1, number2), headers);
            SumResponse sumResponse = restTemplate.postForObject(sumResourceUrl, request, SumResponse.class);
            return buildResponse(sumResponse.getResult(), HttpStatus.OK);
        }
        if (operation.contains("mult")) {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.setMessageConverters(messageConverters);

            HttpEntity<MultiplicationRequest> request = new HttpEntity<>(new MultiplicationRequest(number1, number2), headers);
            MultiplicationResponse multiplicationResponse = restTemplate.postForObject(multiplyResourceUrl, request, MultiplicationResponse.class);

            return buildResponse(multiplicationResponse.getResult(), HttpStatus.OK);
        }
        return buildResponse(0, HttpStatus.I_AM_A_TEAPOT);
    }

    private ResponseEntity<CalculationResponse> buildResponse(Integer result, HttpStatus status) {
        return new ResponseEntity<>(new CalculationResponse(result), status);
    }
}
