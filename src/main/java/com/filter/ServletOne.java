package com.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "servletOne", urlPatterns = "/filter/servletOne")
public class ServletOne extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("Entering ServletOne.doGet() ");
		resp.getWriter().println("Servlet One");
		System.out.println("Leaving ServletOne.doGet() ");
	}
}
