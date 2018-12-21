<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%--@elvariable id="contacts" type="java.util.Set<com.addresscontact.Contact>" --%>

<!DOCTYPE html>
<html>

<head>
<title><fmt:message>${title.browser }</fmt:message></title>
</head>

<body>
	<h2>
		<fmt:message key="title.page"></fmt:message>
	</h2>
	<c:choose>
		<c:when test="${fn:length(contacts)==0 }">
			<i><fmt:message key="message.noContacts"></fmt:message></i>
		</c:when>
		<c:otherwise>
			<c:forEach items="${contacts }" var="contact">
				<b> <c:out
						value="=== ${contact.firstName }, ${contact.lastName } ==="></c:out>
				</b>
				<br>
				<c:out value="PhoneNumber: ${contact.getPhoneNumber() }" />
				<br>
				<c:out value="Address: ${contact.getAddress() }"></c:out>
				<br>
				<c:if test="${contact.birthday !=null}">

					<fmt:message key="label.birthday"></fmt:message>
					<c:out value=": ${contact.birthday }"></c:out>
					<br>
				</c:if>
				<fmt:message key="label.creationDate"></fmt:message>
				<c:out value=": ${contact.dateCreated }" />
				<br>
				<br>
			</c:forEach>
		</c:otherwise>
	</c:choose>
</body>
</html>
