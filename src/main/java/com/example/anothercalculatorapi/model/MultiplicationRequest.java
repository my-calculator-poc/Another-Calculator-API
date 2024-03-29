package com.example.anothercalculatorapi.model;

public class MultiplicationRequest {
    private Integer number1;
    private Integer number2;

    public MultiplicationRequest() {
    }

    public MultiplicationRequest(final Integer number1, final Integer number2) {
        this.number1 = number1;
        this.number2 = number2;
    }

    public Integer getNumber1() {
        return number1;
    }

    public Integer getNumber2() {
        return number2;
    }

    public void setNumber1(Integer number1) {
        this.number1 = number1;
    }

    public void setNumber2(Integer number2) {
        this.number2 = number2;
    }
}