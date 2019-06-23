package com.example.anothercalculatorapi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
public class CalculatorController {

    @GetMapping(value = "/calc")
    public ResponseEntity<CalculationResponse> calc(@RequestParam("number1") Integer number1,
                                                    @RequestParam("number2") Integer number2,
                                                    @RequestParam("op") String operation) {
        if (operation.contains("sum")) {
            return buildResponse(number1 + number2, HttpStatus.OK);
        }
        if (operation.contains("mult")) {
            return buildResponse(number1 * number2, HttpStatus.OK);
        }
        return buildResponse(0, HttpStatus.I_AM_A_TEAPOT);
    }

    private ResponseEntity<CalculationResponse> buildResponse(Integer result, HttpStatus status) {
        return new ResponseEntity<>(new CalculationResponse(result), status);
    }
}
