package com.ai.st.microservice.operators;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class StMicroserviceOperatorsApplication {

    public static void main(String[] args) {
        SpringApplication.run(StMicroserviceOperatorsApplication.class, args);
    }

}
