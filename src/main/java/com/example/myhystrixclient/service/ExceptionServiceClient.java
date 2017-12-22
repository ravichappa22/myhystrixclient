package com.example.myhystrixclient.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value="myrestfeign", url="http://localhost:8070/")
public interface ExceptionServiceClient {

	@RequestMapping("/feignname")
	public String getFeignname();
}
