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

import feign.FeignException;

@Service
public class MyBookImplementation {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MyBookImplementation.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ExceptionServiceClient exceptionServiceClient;
	

	/*@HystrixCommand(fallbackMethod="fallbackBooks", commandProperties = {
		      @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),   /* this is request connection time out value 
		      @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"), /* This property sets the time after which  the circuit will be closed to try again
		      @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"), /* Mimimum number of requests to be observed to open the circuit 
		      @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50") /* Percentage of failures in a rolling window that trips the circuit to open 
		 })*/
	public String getBookName(){
		String responseBody = null;
		// add exception logic here
		try {
			// response = restTemplate.exchange("http://localhost:8070/feignname", HttpMethod.GET, null, String.class);
			responseBody = exceptionServiceClient.getFeignname();
			 //responseBody = response.getBody();
			LOGGER.info("debug log calling servicein books");
			//throw new FrameworkValidationError("1000");
			
		} catch (FeignException  e) {
			LOGGER.error("Exception Occured in getBookName=" + e.getMessage());
			Map<String, Object> details = new HashMap<>();
			details.put("claimNumber", "123456");
			details.put("generator", "RestService");
			details.put("sourceOfError", "MemberValidation");
			FrameworkValidationError frameworkValidationError = new FrameworkValidationError("1000", details,e);
			Message exceptionMessage = new Message(e.getMessage());
			frameworkValidationError.setErrorMessage(exceptionMessage);
			
			//create detailed messages
			
			exceptionMessage.setCode("1001");
			String[] subs = new String[1];
			subs[0] = "123456 ";
			exceptionMessage.setArguments(subs);
			List<Message> messageList = new ArrayList<Message>();
			messageList.add(exceptionMessage);
		//	frameworkValidationError.setValidationMessages(messageList);
			
			throw frameworkValidationError;
		
			
		}
		return responseBody;
	}
	
	public String fallbackBooks(FeignException e){
		LOGGER.error("Exception catched in fallback" + e);
		
		
		return "Fallback Tata McGrawhill Book";
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
