package com.tickets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;

import com.tickets.chat.ChatEndpoint;

@WebServlet(name = "chatServlet", urlPatterns = "/chat")
public class ChatServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");
		if ("list".equals(action)) {
			req.setAttribute("chatRooms", ChatEndpoint.currentChatRooms);
			req.getRequestDispatcher("/WEB-INF/jsp/tickets/listChat.jsp").forward(req, resp);
			return;
		} else {
			resp.sendRedirect("tickets");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("Expires", "Thu, 1 Jan 1970 12:00:00 GMT");
		resp.setHeader("Cache-Control", "max-age=0, must-revalidate, no-cache");

		String action = req.getParameter("action");
		String view = null;
		switch (action) {
		case "new":
			req.setAttribute("roomId", 0);
			view = "chat";
			break;
		case "join":
			String id = req.getParameter("roomId");
			if (id == null || NumberUtils.isDigits(id) == false) {
				resp.sendRedirect("chat?list");
			} else {
				req.setAttribute("roomId", Long.parseLong(id));
				view = "chat";
			}
			break;
		default:
			resp.sendRedirect("tickets");
			break;
		}
		if (view != null)
			req.getRequestDispatcher("/WEB-INF/jsp/tickets/" + view + ".jsp").forward(req, resp);
	}
}
