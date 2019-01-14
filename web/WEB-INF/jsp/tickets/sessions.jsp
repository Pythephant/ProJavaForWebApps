<%@ page import="java.util.Date" %>
<template:basic htmlTitle="Current Sessions" bodyTitle="Sessions">
	<jsp:useBean id="currentTime" class="java.util.Date" />
	<c:forEach items="${sessionsMap}" var="entry">
		${entry.value.getId()} - ${entry.value.getAttribute("username") }
		<c:if test="${entry.value.getId()==pageContext.session.id }">
			<b> (you)</b>
		</c:if>
		, last accessed at ${(currentTime.time-entry.value.getLastAccessedTime())/1000 } seconds.
		<br/>
	</c:forEach>


</template:basic>