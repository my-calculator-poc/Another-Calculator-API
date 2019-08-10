package com.example.anothercalculatorapi;

import com.example.anothercalculatorapi.model.MultiplicationRequest;
import com.example.anothercalculatorapi.model.MultiplicationResponse;
import com.example.anothercalculatorapi.model.SumRequest;
import com.example.anothercalculatorapi.model.SumResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController()
public class CalculatorController {
    String sumResourceUrl = "http://localhost:8080/sum";
    String multiplyResourceUrl = "http://localhost:8081/multiply";

    @GetMapping(value = "/calc")
    public ResponseEntity<CalculationResponse> calc(@RequestParam("number1") Integer number1,
                                                    @RequestParam("number2") Integer number2,
                                                    @RequestParam("op") String operation) {
        RestTemplate restTemplate = new RestTemplate();
        if (operation.contains("sum")) {
            HttpEntity<SumRequest> request = new HttpEntity<>(new SumRequest(number1, number2));
            SumResponse sumResponse = restTemplate.postForObject(sumResourceUrl, request, SumResponse.class);
            return buildResponse(sumResponse.getResult(), HttpStatus.OK);
        }
        if (operation.contains("mult")) {
            HttpEntity<MultiplicationRequest> request = new HttpEntity<>(new MultiplicationRequest(number1, number2));
            MultiplicationResponse multiplicationResponse = restTemplate.postForObject(multiplyResourceUrl, request, MultiplicationResponse.class);
            return buildResponse(multiplicationResponse.getResult(), HttpStatus.OK);
        }
        return buildResponse(0, HttpStatus.I_AM_A_TEAPOT);
    }

    private ResponseEntity<CalculationResponse> buildResponse(Integer result, HttpStatus status) {
        return new ResponseEntity<>(new CalculationResponse(result), status);
    }
}
