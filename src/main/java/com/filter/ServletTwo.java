package com.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "servletTwo", urlPatterns = "/filter/servletTwo")
public class ServletTwo extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("Entering ServletTwo.doGet() ");
		resp.getWriter().println("Servlet Two");
		System.out.println("Leaving ServletTwo.doGet() ");
	}
}
