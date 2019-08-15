package com.example.anothercalculatorapi;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "endpoint")
public class ApplicationProperties {

    private String sum;
    private String multiply;

    public String getSum() {
        return this.sum;
    }

    public String getMultiply() {
        return this.multiply;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public void setMultiply(String multiply) {
        this.multiply = multiply;
    }

}