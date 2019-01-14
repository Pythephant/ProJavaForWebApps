<template:basic htmlTitle="Login Page" bodyTitle="Login">
	<c:if test="${loginFailed}">
		<c:out value="Login failed. please try again" />
	</c:if>

	<form method="POST" action="<c:url value="/login" />">
		Username:<input type="text" name="username" /> <br> <br>
		Password:<input type="password" name="passwd"><br> <br>
		<input type="submit" value="Login">
	</form>
</template:basic>