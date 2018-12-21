package com.addressbook;

import java.io.IOException;
import java.time.Instant;
import java.time.Month;
import java.time.MonthDay;
import java.util.Collections;
import java.util.Locale;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.core.Config;

@WebServlet(name = "addressContactServlet", urlPatterns = "/addresscontact")
public class AddressContactServlet extends HttpServlet {
	private static final SortedSet<Contact> contacts = new TreeSet<>();
	static {
		contacts.add(new Contact("Jane", "Sanders", "555-1593", "394 E 22nd Ave", MonthDay.of(Month.JANUARY, 5),
				Instant.now()));
		contacts.add(new Contact("John", "Smith", "555-0712", "315 Maple St", null, Instant.now()));
		contacts.add(new Contact("Scott", "Johnson", "555-9834", "424 Oak Dr", MonthDay.of(Month.NOVEMBER, 17),
				Instant.now()));
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String language = request.getParameter("language");
		if ("french".equalsIgnoreCase(language))
			Config.set(request, Config.FMT_LOCALE, Locale.FRANCE);
		if (request.getParameter("empty") != null)
			request.setAttribute("contacts", Collections.<Contact>emptySet());
		else
			request.setAttribute("contacts", contacts);
		request.getRequestDispatcher("/WEB-INF/jsp/addrcontact/contact.jsp").forward(request, response);
	}
}
