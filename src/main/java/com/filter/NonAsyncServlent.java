package com.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name="nonAsyncServlet",urlPatterns="/filter/regular")
public class NonAsyncServlent extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("Entering NonAsyncServlet.doGet() .");
		req.getRequestDispatcher("/WEB-INF/jsp/filter/nonAsync.jsp").forward(req, resp);;
		System.out.println("Leaving NonAsynServlet.doGet() .");
				
	}
}
