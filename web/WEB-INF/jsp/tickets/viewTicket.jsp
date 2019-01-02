<%@ page import="com.tickets.Ticket"%>
<%@ page import="com.tickets.Attachment"%>

<!DOCTYPE html>
<html>
<head>
<title>Customer Support</title>
</head>

<body>
	<h2>Ticket #${ticketId }:${ticket.subject }</h2>
	<i>Customer Name: ${ticket.customerName }</i>
	<br>
	<br> ${ticket.getBody() }
	<br>
	<br>
	<c:if test="${ticket.getNumberOfAttachments()>0 }">
		
		Attachemnts:
		<c:forEach items="${ticket.getAttachments()}" var="a" varStatus="status">
			<c:if test="${!status.first}">, </c:if>
			<a
				href="<c:url value="tickets">
					<c:param name="action" value="download"/>
					<c:param name="ticketId" value="${ticketId }" />
					<c:param name="attachment" value="${a.name }%>" />
				</c:url>">${a.name }</a>
			
		</c:forEach>
	</c:if>
	
	<br>
	<br>
	<br>
	<a href="<c:url value="/tickets"></c:url>">Return to list Tickets</a>
</body>
</html>