package com.helloworld;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name="helloServlet",urlPatterns="/hello")
public class HelloServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.getWriter().println("Hello, World!" + this.getServletName());
	}

	@Override
	public void init() throws ServletException {
		System.out.println("Servlet " + this.getServletName() + " Inited");
	}

	@Override
	public void destroy() {
		System.out.println("Servlet " + this.getServletName() + " has destroyed");
	}

}
