package com.finalproject.breeding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing //타임스템프
@SpringBootApplication
public class BreedingApplication {
    public static void main(String[] args) {
        SpringApplication.run(BreedingApplication.class, args);
    }

}
