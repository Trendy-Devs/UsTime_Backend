package com.duhwan.ustime_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class UsTimeBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(UsTimeBackendApplication.class, args);
    }

}
