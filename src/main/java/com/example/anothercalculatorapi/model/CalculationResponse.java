package com.example.anothercalculatorapi.model;

public class CalculationResponse {
    private Integer result;

    public CalculationResponse() {
    }

    public CalculationResponse(final Integer result) {
        this.result = result;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }
}