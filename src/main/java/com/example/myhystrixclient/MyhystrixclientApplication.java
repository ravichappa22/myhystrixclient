package com.example.myhystrixclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.client.RestTemplate;

import com.bff.core.framework.exception.ExceptionAndValidatorUtils;
import com.example.myhystrixclient.service.ExceptionServiceClient;


@SpringBootApplication
@EnableCircuitBreaker
@EnableHystrixDashboard
@EnableFeignClients(basePackageClasses = {ExceptionServiceClient.class})
@EnableEurekaClient
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
	
	@Bean(name = "MessageSource")
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource obj = new ResourceBundleMessageSource();
		obj.setBasename("bundles/messages");
		return obj;
	}
	
	
	@Bean
	public ExceptionAndValidatorUtils exceptionAndValidatorUtils() {
		return new ExceptionAndValidatorUtils();
		
	}
}
