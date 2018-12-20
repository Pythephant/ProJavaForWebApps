<%@ page import="java.util.Map"%>

<html>
<head>
<title>Customer Support</title>
</head>
<body>
	<h2>Sessions</h2>
	<%
		Map<String, HttpSession> sessionsMap = (Map<String, HttpSession>) request.getAttribute("sessionsMap");
	%>
	<p>
		There are a total of
		<%=sessionsMap.size()%>
		active sessions in the application
	</p>
	<%
		long currentTime = System.currentTimeMillis();
		for (HttpSession s : sessionsMap.values()) {
			out.print(s.getId() + " - " + s.getAttribute("username"));
			if (s.getId().equals(session.getId()))
				out.print("(you)");
			out.print(" - last active " + (currentTime - s.getLastAccessedTime()) / 1000 + " seconds.");
			out.println("<br>");
		}
	%>
</body>
</html>