package com.tickets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "sessionServlet", urlPatterns = "/sessions")
public class SessionServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("sessionsMap", SessionRegistry.getAllSessions());
		req.getRequestDispatcher("/WEB-INF/jsp/tickets/sessions.jsp").forward(req, resp);
	}
}
