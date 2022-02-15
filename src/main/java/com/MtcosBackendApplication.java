package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com")
public class MtcosBackendApplication {


    public static void main(String[] args) {
        SpringApplication.run(MtcosBackendApplication.class, args);
    }

}
