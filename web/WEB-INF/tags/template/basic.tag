<%@ tag body-content="scriptless" trimDirectiveWhitespaces="false"%>
<%@ attribute name="htmlTitle" type="java.lang.String"
	rtexprvalue="true" required="true"%>
<%@ attribute name="bodyTitle" type="java.lang.String"
	rtexprvalue="true" required="true"%>
<%@ attribute name="extraHeadContent" fragment="true" required="false"%>
<%@ attribute name="extraNavigationContent" fragment="true"
	required="false"%>
<%@ include file="/WEB-INF/jsp/base.jspf"%>
<template:main htmlTitle="${htmlTitle }" bodyTitle="${bodyTitle }">
	<jsp:attribute name="headContent">
		<jsp:invoke fragment="extraHeadContent" />
	</jsp:attribute>
	<jsp:attribute name="navigationContent">
		<a href="<c:url value="/tickets"/>">list tickets</a>
		<br>
		<a
			href="<c:url value="/tickets">
					<c:param name="action" value="create" />
				</c:url>">create ticket</a>
		<br>
		<a href="<c:url value="/sessions"/>">
			sessions
		</a>
		<br>
		<c:choose>
			<c:when test="${username!=null }">
				<a href="<c:url value="/login?logout" />">Logout</a>
			</c:when>
			<c:otherwise>
				<a href="<c:url value="/login"/>">Login</a>
			</c:otherwise>
		</c:choose>
		<jsp:invoke fragment="extraNavigationContent"></jsp:invoke>
	</jsp:attribute>
	<jsp:body>
        <jsp:doBody />
    </jsp:body>
</template:main>

<%-- <template:main htmlTitle="${htmlTitle}" bodyTitle="${bodyTitle}">
    <jsp:attribute name="headContent">
        <jsp:invoke fragment="extraHeadContent" />
    </jsp:attribute>
    <jsp:attribute name="navigationContent">
        <a href="<c:url value="/tickets" />">List Tickets</a><br />
        <a href="<c:url value="/tickets">
            <c:param name="action" value="create" />
        </c:url>">Create Ticket</a><br />
        <a href="<c:url value="/sessions" />">List Sessions</a><br />
        <a href="<c:url value="/login?logout" />">Log Out</a><br />
        <jsp:invoke fragment="extraNavigationContent" />
    </jsp:attribute>
    <jsp:body>
        <jsp:doBody />
    </jsp:body>
</template:main> --%>
