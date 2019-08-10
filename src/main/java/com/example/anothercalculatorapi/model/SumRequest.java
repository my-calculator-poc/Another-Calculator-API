package com.example.anothercalculatorapi.model;

public class SumRequest {

    private Integer number1;
    private Integer number2;

    public SumRequest() {

    }

    public SumRequest(Integer number1, Integer number2) {
        this.number1 = number1;
        this.number2 = number2;
    }

    public Integer getNumber1() {
        return number1;
    }

    public Integer getNumber2() {
        return number2;
    }
}
