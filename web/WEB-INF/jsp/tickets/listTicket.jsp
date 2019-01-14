<%@page import="java.util.Map,com.tickets.*"%>
<template:basic htmlTitle="List Tickets" bodyTitle="List Tickets">
	<a
		href="<c:url value="/tickets">
			<c:param name="action" value="create" />
	</c:url>">Create
		Ticket</a>
	<br>
	<c:choose>
		<c:when test="${ticketDatabase.size()<=0 }">There are no tickets in the system.</c:when>
		<c:otherwise>
			<c:forEach items="${ticketDatabase}" var="entry">
				Ticket # ${entry.key} :
				<a
					href="<c:url value="/tickets">
							<c:param name="action" value="view" />
							<c:param name="ticketId" value="${entry.key }" />
						</c:url>">${entry.value.getSubject() }</a>
						(customer:<c:out value="${entry.value.customerName }" />)<br>
			</c:forEach>
		</c:otherwise>
	</c:choose>
</template:basic>