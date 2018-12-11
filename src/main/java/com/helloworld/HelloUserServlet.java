package com.helloworld;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "helloUser", urlPatterns = { "/greeting", "/wazzup" })
public class HelloUserServlet extends HttpServlet {

	private static final String DEFAULT_USER = "GUEST";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<String, String[]> params = req.getParameterMap();

		System.out.println(Arrays.toString(params.entrySet().toArray()));
		System.out.println(req.getContentType());
		System.out.println(req.getContentLength());
		System.out.println(req.getRequestURL());
		System.out.println(req.getRequestURI());
		System.out.println(req.getServletPath());
		System.out.println(req.getHeaderNames());
		System.out.println("===================");

		String user;
		if (params.get("user") == null)
			user = HelloUserServlet.DEFAULT_USER;
		else
			user = params.get("user")[0];
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter writer = resp.getWriter();
		writer.append("<!DOCTYPE html>\r\n").append("<html>\r\n").append(" <head>\r\n")
				.append(" <title>Hello User Application</title>\r\n").append(" </head>\r\n").append(" <body>\r\n")
				.append(" Hello, ").append(user).append("!<br/><br/>\r\n")
				.append(" <form action=\"wazzup\" method=\"POST\">\r\n").append(" Enter your name:<br/>\r\n")
				.append(" <input type=\"text\" name=\"user\"/><br/>\r\n")
				.append(" <input type=\"submit\" value=\"Submit\"/>\r\n").append(" </form>\r\n").append(" </body>\r\n")
				.append("</html>\r\n");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}
}
