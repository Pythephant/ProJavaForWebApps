package com.tickets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet(name = "ticketServlet", urlPatterns = "/tickets")
@MultipartConfig(fileSizeThreshold = 5_242_880, maxFileSize = 20_971_520L, maxRequestSize = 41_943_040L)
public class TicketServlet extends HttpServlet {
	private volatile int TICKET_ID_SEQUENCE = 1;

	private Map<Integer, Ticket> ticketDatabase = new LinkedHashMap<>();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(req.getSession().getAttribute("username")==null) {
			resp.sendRedirect("login");
			return;
		}
		String action = req.getParameter("action");
		if (action == null)
			action = "list";
		switch (action) {
		case "create":
			this.showTicketForm(req, resp);
			break;
		case "view":
			this.viewTicket(req, resp);
			break;
		case "download":
			this.downLoadAttachment(req, resp);
			break;
		case "list":
		default:
			this.listTickets(req, resp);
			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String action = req.getParameter("action");
		if (action == null)
			action = "list";
		switch (action) {
		case "create":
			this.createTicket(req, resp);
			break;
		case "list":
		default:
			resp.sendRedirect("tickets");
			break;
		}
	}

	private void showTicketForm(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		request.getRequestDispatcher("/WEB-INF/jsp/tickets/ticketForm.jsp").forward(request, response);
	}

	private void viewTicket(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String idString = request.getParameter("ticketId");
		Ticket ticket = this.getTicket(idString, response);
		if (ticket == null)
			return;

		request.setAttribute("ticketId", idString);
		request.setAttribute("ticket", ticket);
		request.getRequestDispatcher("/WEB-INF/jsp/tickets/viewTicket.jsp").forward(request, response);
	}

	private void downLoadAttachment(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String idString = request.getParameter("ticketId");
		Ticket ticket = this.getTicket(idString, response);
		if (ticket == null)
			return;

		String name = request.getParameter("attachment");
		if (name == null) {
			response.sendRedirect("tickets?action=view&ticketId=" + idString);
			return;
		}

		Attachment attachment = ticket.getAttachment(name);
		if (attachment == null) {
			response.sendRedirect("tickets?action=view&ticketId=" + idString);
			return;
		}

		response.setHeader("Content-Disposition", "attachment; filename=" + attachment.getName());
		response.setContentType("application/octet-stream");

		ServletOutputStream stream = response.getOutputStream();
		stream.write(attachment.getContents());
	}

	private void listTickets(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		request.setAttribute("ticketDatabase", this.ticketDatabase);
		request.getRequestDispatcher("/WEB-INF/jsp/tickets/listTicket.jsp").forward(request, response);
	}

	private void createTicket(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Ticket ticket = new Ticket();
		ticket.setCustomerName(request.getParameter("customerName"));
		ticket.setSubject(request.getParameter("subject"));
		ticket.setBody(request.getParameter("body"));

		Part filePart = request.getPart("file1");
		if (filePart != null && filePart.getSize() > 0) {
			Attachment attachment = this.processAttachment(filePart);
			if (attachment != null)
				ticket.addAttachment(attachment);
		}
		int id;
		synchronized (this) {
			id = this.TICKET_ID_SEQUENCE++;
			this.ticketDatabase.put(id, ticket);
		}

		response.sendRedirect("tickets?action=view&ticketId=" + id);
	}

	private Attachment processAttachment(Part filePart) throws IOException {
		InputStream inputStream = filePart.getInputStream();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		int read;
		final byte[] bytes = new byte[1024];

		while ((read = inputStream.read(bytes)) != -1) {
			outputStream.write(bytes, 0, read);
		}

		Attachment attachment = new Attachment();
		attachment.setName(filePart.getSubmittedFileName());
		attachment.setContents(outputStream.toByteArray());

		return attachment;
	}

	private Ticket getTicket(String idString, HttpServletResponse response) throws IOException {
		if (idString == null || idString.length() == 0) {
			response.sendRedirect("tickets");
			return null;
		}

		try {
			Ticket ticket = this.ticketDatabase.get(Integer.parseInt(idString));
			if (ticket == null) {
				response.sendRedirect("tickets");
				return null;
			}
			return ticket;
		} catch (Exception e) {
			response.sendRedirect("tickets");
			return null;
		}
	}

	private PrintWriter writeHeader(HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");

		PrintWriter writer = response.getWriter();
		writer.append("<!DOCTYPE html>\r\n").append("<html>\r\n").append("    <head>\r\n")
				.append("        <title>Customer Support</title>\r\n").append("    </head>\r\n")
				.append("    <body>\r\n");

		return writer;
	}

	private void writeFooter(PrintWriter writer) {
		writer.append("    </body>\r\n").append("</html>\r\n");
	}
}
