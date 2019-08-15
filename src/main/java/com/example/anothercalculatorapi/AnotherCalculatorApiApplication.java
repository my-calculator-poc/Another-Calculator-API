package com.example.anothercalculatorapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties.class)
public class AnotherCalculatorApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnotherCalculatorApiApplication.class, args);
	}

}
