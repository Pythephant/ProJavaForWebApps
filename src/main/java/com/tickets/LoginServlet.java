package com.tickets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "loginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
	private static Map<String, String> userDatabase = new HashMap<>();
	static {
		userDatabase.put("jason", "jason");
		userDatabase.put("canddi", "canddi");
		userDatabase.put("admin", "admin");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		if (req.getParameter("logout") != null) {
			session.invalidate();
			resp.sendRedirect("login");
			return;
		}
		if (session.getAttribute("username") != null) {
			resp.sendRedirect("tickets");
			return;
		}
		req.setAttribute("loginFailed", false);
		req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		if (session.getAttribute("username") != null) {
			resp.sendRedirect("tickets");
			return;
		}

		String username = req.getParameter("username");
		String passwd = req.getParameter("passwd");
		if (username == null || passwd == null || LoginServlet.userDatabase.containsKey(username) == false
				|| passwd.equals(LoginServlet.userDatabase.get(username)) == false) {
			req.setAttribute("loginFailed", true);
			req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
		} else {
			session.setAttribute("username", username);
			req.changeSessionId();
			resp.sendRedirect("tickets");
		}

	}
}
