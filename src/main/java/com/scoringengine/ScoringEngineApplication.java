package com.scoringengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableRetry
@SpringBootApplication
public class ScoringEngineApplication {


    public static void main(String[] args) {
        SpringApplication.run(ScoringEngineApplication.class, args);
    }


}
