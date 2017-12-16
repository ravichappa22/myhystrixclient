package com.example.myhystrixclient.service;

import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class MyBookImplementation {
	
	@HystrixCommand(fallbackMethod="fallbackBooks")
	public String getBookName(){
		return "This is my JAVA 8 Certification Book";
	}
	
	@HystrixCommand(fallbackMethod = "defaultStores")
	public String fallbackBooks(){
		return "Tata McGrawhill Book";
	}

}
