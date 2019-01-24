package com.logger;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.ThreadContext;


public class LoggingFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		boolean clear = false;
		if (ThreadContext.containsKey("id") == false) {
			clear = true;
			ThreadContext.put("id", UUID.randomUUID().toString());
			HttpSession session = ((HttpServletRequest) arg0).getSession();
			if (session != null) {
				ThreadContext.put("username", (String) session.getAttribute("username"));
			}
		}

		try {
			arg2.doFilter(arg0, arg1);
		} finally {
			if (clear) {
				ThreadContext.clearAll();
			}
		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
