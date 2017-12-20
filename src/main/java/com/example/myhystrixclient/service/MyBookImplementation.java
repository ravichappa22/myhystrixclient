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

import com.bff.core.framework.exception.FrameworkValidationError;
import com.bff.core.framework.exception.Message;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class MyBookImplementation {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MyBookImplementation.class);
	
	@Autowired
	private RestTemplate restTemplate;
	

	@HystrixCommand(fallbackMethod="fallbackBooks")
	public String getBookName(){
		String responseBody = null;
		//try {
		//ResponseEntity<String>  response = template.getForObject("http://localhost:8070/feignname", String.class);
		ResponseEntity<String>  response = restTemplate.exchange("http://localhost:8070/feignname", HttpMethod.GET, null, String.class);
		responseBody = response.getBody();
	/*	if(response.getStatusCode().is2xxSuccessful()) {
			return responseBody;
		}else if(response.hasBody() && response.getBody() != null) {
			ObjectMapper objectMapper = new ObjectMapper();
			BusinessException businessException=null;
			try {
				 businessException =  objectMapper.readValue(response.getBody(), BusinessException.class);
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			Map<String, Object> details = new HashMap<>();
			details.put("claimNumber", "123456");
			details.put("generator", "RestService");
			details.put("sourceOfError", "MemberValidation");
			FrameworkValidationError frameworkValidationError = new FrameworkValidationError("1000", businessException.getDetailMap());
			
			//create detailed messages
			Message exceptionMessage = new Message();
			exceptionMessage.setCode("1001");
			String[] subs = new String[1];
			subs[0] = "123456 " + businessException.getMessage();
			exceptionMessage.setArguments(subs);
			List<Message> messageList = new ArrayList<Message>();
			messageList.add(exceptionMessage);
			frameworkValidationError.setValidationMessages(messageList);
			LOGGER.info("framework error=" + frameworkValidationError.toString());
			
			throw frameworkValidationError;
		}*/
		
		/*try {
	        if (RestUtil.isError(response.getStatusCode())) {
	         	Map<String, Object> details = new HashMap<>();
				details.put("claimNumber", "123456");
				details.put("generator", "RestService");
				details.put("sourceOfError", "MemberValidation");
				FrameworkValidationError frameworkValidationError = new FrameworkValidationError("1000", details);
				
				//create detailed messages
				Message exceptionMessage = new Message();
				exceptionMessage.setCode("1001");
				String[] subs = new String[1];
				//subs[0] = "123456" + e.getMessage();
				exceptionMessage.setArguments(subs);
				List<Message> messageList = new ArrayList<Message>();
				messageList.add(exceptionMessage);
				frameworkValidationError.setValidationMessages(messageList);
				
				throw frameworkValidationError;
	        } else {
	            //DoodadResources doodads = objectMapper.readValue(responseBody, DoodadResources.class);
	            return responseBody;
	        }
	      }catch (RuntimeException e) {
	        throw new RuntimeException(e);
	    }*/
		
	/*	}catch(RuntimeException e) {
			LOGGER.error("Exception Occured=" + e);
			if(e instanceof FrameworkValidationError) {
				LOGGER.error("this is instance of FrameworkError");
			}
			//create top error
			Map<String, Object> details = new HashMap<>();
			details.put("claimNumber", "123456");
			details.put("generator", "RestService");
			details.put("sourceOfError", "MemberValidation");
			FrameworkValidationError frameworkValidationError = new FrameworkValidationError("1000", details,e);
			
			//create detailed messages
			Message exceptionMessage = new Message();
			exceptionMessage.setCode("1001");
			String[] subs = new String[1];
			subs[0] = "123456" + e.getMessage();
			exceptionMessage.setArguments(subs);
			List<Message> messageList = new ArrayList<Message>();
			messageList.add(exceptionMessage);
			frameworkValidationError.setValidationMessages(messageList);
			
			throw frameworkValidationError;
		}
		//return "This is my JAVA 8 Certification Book";*/
		return responseBody;
	}
	
	public String fallbackBooks(Throwable e) throws FrameworkValidationError{
		LOGGER.error("Exception Occured=" + e);
		if(e instanceof FrameworkValidationError) {
			LOGGER.error("this is instance of FrameworkError");
		}
		//create top error
		Map<String, Object> details = new HashMap<>();
		details.put("claimNumber", "123456");
		details.put("generator", "RestService");
		details.put("sourceOfError", "MemberValidation");
		FrameworkValidationError frameworkValidationError = new FrameworkValidationError("1000", details,e);
		
		//create detailed messages
		Message exceptionMessage = new Message();
		exceptionMessage.setCode("1001");
		String[] subs = new String[1];
		subs[0] = "123456" + e.getMessage();
		exceptionMessage.setArguments(subs);
		List<Message> messageList = new ArrayList<Message>();
		messageList.add(exceptionMessage);
		frameworkValidationError.setValidationMessages(messageList);
		
		throw frameworkValidationError;
	}
	//	return "Fallback Tata McGrawhill Book";
	

}
