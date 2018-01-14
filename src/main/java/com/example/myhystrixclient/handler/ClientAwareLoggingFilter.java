package com.example.myhystrixclient.handler;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;


@Component
public class ClientAwareLoggingFilter implements Filter{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ClientAwareLoggingFilter.class);

	@Override
	public void destroy() {
		LOGGER.info("Servlet Initialized");
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		if (req instanceof HttpServletRequest) {
			final HttpServletRequest httpRequest = (HttpServletRequest) req;
			final String CORRELATIONID = httpRequest.getHeader("X-CORRELATIONID");
			if (CORRELATIONID != null) {
				MDC.put("X-CORRELATIONID", CORRELATIONID);
			}else {
				MDC.put("X-CORRELATIONID", UUID.randomUUID().toString());
			}
		} 
		chain.doFilter(req, res);

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
		//nothing to do
	}

	
}
