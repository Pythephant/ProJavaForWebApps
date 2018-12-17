<%@ page contentType="text/html; charset=UTF-8"%>
<%!private static final String DEFAULT_USER = "Guest";%>

<%
	request.setCharacterEncoding("UTF-8");
	String user = request.getParameter("user");
	if (user == null) {
		user = DEFAULT_USER;
	}
%>

<html>
<head>
<title>Hello User Jsp</title>
</head>
<body>
	Hello,
	<%=user%><br />
	<br>
	<form action="hello-user.jsp" method="POST">
		Enter the user Name: <br/>
		<input type="text" name="user"><br/>
		<input type="submit" value="快说hello">
	</form>
</body>
</html>