package com.spring.participations.participations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;

@SpringBootApplication
@EnableDiscoveryClient
public class ParticipationsApplication {

    @LoadBalanced
    public static void main(String[] args) {
        SpringApplication.run(ParticipationsApplication.class, args);
    }

}
