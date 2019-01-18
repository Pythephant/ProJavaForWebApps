package com.tickets;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Configuror implements  ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext context = event.getServletContext();
		FilterRegistration.Dynamic registry = context.addFilter("authenticationFilter", new AuthenticationFilter());
		registry.setAsyncSupported(true);
		registry.addMappingForUrlPatterns(null, false, "/tickets","/sessions","/chat");
		
	}

}
