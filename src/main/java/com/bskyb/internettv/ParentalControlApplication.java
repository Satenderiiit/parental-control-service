package com.bskyb.internettv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class ParentalControlApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParentalControlApplication.class, args);
    }
}
