package com.example.anothercalculatorapi;

import com.example.anothercalculatorapi.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import static com.example.anothercalculatorapi.Util.getMessageConverters;

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
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(getMessageConverters());

        if (operation.contains("sum")) {
            HttpEntity<SumRequest> request = new HttpEntity<>(new SumRequest(number1, number2), headers);
            SumResponse sumResponse = restTemplate.postForObject(sumResourceUrl, request, SumResponse.class);

            return buildResponse(sumResponse.getResult(), HttpStatus.OK);
        }
        if (operation.contains("mult")) {
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
