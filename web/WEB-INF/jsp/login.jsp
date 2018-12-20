<!DOCTYPE unspecified PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Customer Support</title>
</head>
<body>
	<h2>Login</h2>
	You must login to access the customer support site.
	<br>
	<br>
	<%
		if (((Boolean) request.getAttribute("loginFailed")))
			out.println("Login failed. please try again");
	%>
	<form method="POST" action="<c:url value="/login" />">
		Username:<input type="text" name="username"/> <br><br>
		Password:<input type="password" name="passwd"><br><br>
		<input type="submit" value="Login">
	</form>
</body>
</html>