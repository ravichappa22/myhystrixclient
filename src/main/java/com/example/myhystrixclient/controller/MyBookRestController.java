package com.example.myhystrixclient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.myhystrixclient.service.MyBookImplementation;

@RestController
public class MyBookRestController {
	
	@Autowired
	private MyBookImplementation myBookImplementation;
	
	@RequestMapping(value="/book", produces="application/json", method=RequestMethod.GET)
	public String getBookName(){
		return myBookImplementation.getBookName();
	}

}
