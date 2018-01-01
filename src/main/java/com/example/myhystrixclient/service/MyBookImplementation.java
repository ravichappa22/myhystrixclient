package com.example.myhystrixclient.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bff.core.framework.exception.FrameworkError;
import com.bff.core.framework.exception.FrameworkValidationError;
import com.bff.core.framework.exception.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import feign.FeignException;

@Service
public class MyBookImplementation {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MyBookImplementation.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ExceptionServiceClient exceptionServiceClient;
	

	@HystrixCommand(fallbackMethod="fallbackBooks", commandProperties = {
		      @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),  
		      @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"), 
		      @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),  
		      @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50")  
		 })
	public String getBookName(){
		String responseBody = null;
		// add exception logic here
		//try {
			// response = restTemplate.exchange("http://localhost:8070/feignname", HttpMethod.GET, null, String.class);
			responseBody = exceptionServiceClient.getFeignname();
			 //responseBody = response.getBody();
			LOGGER.info("debug log calling servicein books");
			//throw new FrameworkValidationError("1000");
			
		//} catch (FeignException  e) {
			//LOGGER.error("Exception Occured in getBookName=" + e.getMessage().substring(e.getMessage().indexOf(":")+1));
			////ObjectMapper mapper = new ObjectMapper();
			//FrameworkValidationError frameworkValidationError = mapper.convertValue(e.getMessage().substring(e.getMessage().indexOf(":")+1), FrameworkValidationError.class);
		/*	Map<String, Object> details = new HashMap<>();
			details.put("claimNumber", "123456");
			details.put("generator", "RestService");
			details.put("sourceOfError", "MemberValidation");
			FrameworkValidationError frameworkValidationError = new FrameworkValidationError("1000", details,e);
		
			Message exceptionMessage = new Message(e.getMessage().substring(e.getMessage().indexOf(":")+1));
			frameworkValidationError.setErrorMessage(exceptionMessage);
			
			//create detailed messages
			
			exceptionMessage.setCode("1001");
			String[] subs = new String[1];
			subs[0] = "123456 ";
			exceptionMessage.setArguments(subs);
			List<Message> messageList = new ArrayList<Message>();
			messageList.add(exceptionMessage);
			frameworkValidationError.setValidationMessages(messageList);*/
		//	LOGGER.error("frameworkValidationError=" + frameworkValidationError);
		//	throw frameworkValidationError;
		
			
		//}
		return responseBody;
	}
	
	public String fallbackBooks(Throwable t){
		LOGGER.error("Exception catched in fallback"+t.getMessage());
		Map<String, Object> details = new HashMap<>();
		details.put("claimNumber", "123456");
		details.put("generator", "RestService");
		details.put("sourceOfError", "MemberValidation");
		
		FrameworkValidationError frameworkValidationError = new FrameworkValidationError("1002", details,t);
		
		List<Message> messageList = new ArrayList<Message>();
		Message message = new Message(null, null, t.getMessage());
		messageList.add(message);
		frameworkValidationError.setValidationMessages(messageList);
		throw frameworkValidationError;
		//return "Fallback Tata McGrawhill Book";
	}
	
	
	public String anothername(){
		try {
		ResponseEntity<String>  response = restTemplate.exchange("http://localhost:8070/anothername", HttpMethod.GET, null, String.class);
		return response.getBody();
		}catch(Exception e) {
			throw new FrameworkError("1002", e);
		}
	}
	
	

}
