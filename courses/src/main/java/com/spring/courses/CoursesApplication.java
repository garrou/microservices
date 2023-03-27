package com.spring.courses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;

@SpringBootApplication
@EnableDiscoveryClient
public class CoursesApplication {

	@LoadBalanced
	public static void main(String[] args) {
		SpringApplication.run(CoursesApplication.class, args);
	}

}
