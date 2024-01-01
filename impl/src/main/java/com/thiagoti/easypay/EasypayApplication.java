package com.thiagoti.easypay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EasypayApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasypayApplication.class, args);
    }
}
