package com.example.anothercalculatorapi.model;

class CalculationRequest {
    private Integer number1;
    private Integer number2;

    public CalculationRequest() {
    }

    public CalculationRequest(final Integer number1, final Integer number2) {
        this.number1 = number1;
        this.number2 = number2;
    }

    Integer getNumber1() {
        return number1;
    }

    Integer getNumber2() {
        return number2;
    }

    public void setNumber1(Integer number1) {
        this.number1 = number1;
    }

    public void setNumber2(Integer number2) {
        this.number2 = number2;
    }
}