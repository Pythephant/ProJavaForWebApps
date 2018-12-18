<%@page import="java.util.Map,com.tickets.*"%>
<%
	Map<Integer, Ticket> ticketDatabase = (Map<Integer, Ticket>) request.getAttribute("ticketDatabase");
%>
<html>
<head>
<title>Customer Support</title>
</head>

<body>
	<h2>Tickets</h2>
	<a
		href="<c:url value="/tickets">
			<c:param name="action" value="create" />
	</c:url>">Create
		Ticket</a>
	<br>
	<%
		if (ticketDatabase.size() <= 0) {
			out.println("There are no tickets in the system.");
		} else {
			for (int id : ticketDatabase.keySet()) {
				String idString = Integer.toString(id);
				Ticket ticket = ticketDatabase.get(id);
	%>Ticket #<%=idString%>:
	<a
		href="<c:url value="/tickets">
					<c:param name="action" value="view"/>
					<c:param name="ticketId" value="<%=idString%>" />	
				</c:url>"><%=ticket.getSubject()%></a>
	(customer:
	<%=ticket.getCustomerName()%>)
	<br>
	<%
		}
		}
	%>
</body>
</html>