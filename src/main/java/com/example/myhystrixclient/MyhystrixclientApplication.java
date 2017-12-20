package com.example.myhystrixclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.bff.core.framework.exception.MyResponseErrorHandler;

@SpringBootApplication
@EnableCircuitBreaker
@EnableHystrixDashboard
public class MyhystrixclientApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyhystrixclientApplication.class, args);
	}
	
	@Bean
	public RestTemplate restTemplate() {
		RestTemplate template = new RestTemplate();
	//	template.setErrorHandler(new MyResponseErrorHandler());
		return template;
	}
}
