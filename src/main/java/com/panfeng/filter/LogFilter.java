package com.panfeng.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


public class LogFilter implements Filter{
	
	@Override
	public void destroy() {
		// empty
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		try {
			
			chain.doFilter(request, response);
		} finally {
		}
	}
	@Override
	public void init(FilterConfig config) throws ServletException {
		// empty
		
	}

}
