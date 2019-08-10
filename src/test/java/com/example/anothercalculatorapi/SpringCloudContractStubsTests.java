package com.example.anothercalculatorapi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureStubRunner(
        ids = {"org.jab.microservices:my-sum:0.1.0-SNAPSHOT",
               "com.example:My-Multiply-API:0.0.1-SNAPSHOT"},
        stubsMode = StubRunnerProperties.StubsMode.LOCAL)
public class SpringCloudContractStubsTests {

    @Test
    public void noTest() {

    }
}
