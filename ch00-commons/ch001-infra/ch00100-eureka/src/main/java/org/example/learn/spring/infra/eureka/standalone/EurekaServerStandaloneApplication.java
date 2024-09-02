package org.example.learn.spring.infra.eureka.standalone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerStandaloneApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerStandaloneApplication.class, args);
    }

}