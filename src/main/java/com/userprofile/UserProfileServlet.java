package com.userprofile;

import java.io.IOException;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "userProfileServlet", urlPatterns = "/profile")
public class UserProfileServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = new User();
		user.setUserId(1990L);
		user.setUsername("jason");
		user.setFirstName("Wu>");
		user.setLastName("Jiakun/");

		Hashtable<String, Boolean> permissions = new Hashtable<>();
		permissions.put("user", true);
		permissions.put("modelator", true);
		permissions.put("admin", false);
		user.setPermissions(permissions);

		req.setAttribute("user", user);
		req.getRequestDispatcher("/WEB-INF/jsp/helloprofile/profile.jsp").forward(req, resp);
	}
}
