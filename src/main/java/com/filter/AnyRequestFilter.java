package com.filter;

import java.io.IOException;

import javax.servlet.AsyncContext;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class AnyRequestFilter implements Filter {

	private String name;

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		System.out.println("Entering " + this.name + ".doFilter() .");
		arg2.doFilter(new HttpServletRequestWrapper((HttpServletRequest) arg0),
				new HttpServletResponseWrapper((HttpServletResponse) arg1));
		if (arg0.isAsyncSupported() && arg0.isAsyncStarted()) {
			AsyncContext context = arg0.getAsyncContext();
			System.out.println("Leaving " + this.name + ".doFilter(), async comtext has original request/response "
					+ context.hasOriginalRequestAndResponse());
		}else {
			System.out.println("Leaving " + this.name + ".doFilter() .");
		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		this.name = arg0.getFilterName();

	}

}
